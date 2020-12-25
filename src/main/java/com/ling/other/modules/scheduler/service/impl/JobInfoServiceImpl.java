package com.ling.other.modules.scheduler.service.impl;

import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.DateUtils;
import com.ling.other.modules.scheduler.databoject.ExecutorDO;
import com.ling.other.modules.scheduler.databoject.JobInfoDO;
import com.ling.other.mapper.ExecutorMapper;
import com.ling.other.mapper.JobInfoMapper;
import com.ling.other.modules.scheduler.dto.JobInfoDTO;
import com.ling.other.modules.scheduler.service.IJobService;
import com.ling.other.modules.scheduler.service.JobInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 定时任务业务层
 *
 * @author zhangling
 * @since 2020/9/3 9:53
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Autowired
    private IJobService jobService;

    @Autowired
    private ExecutorMapper executorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobInfoDO createJob(JobInfoDO jobInfo) {

        if (jobInfo.getExecutorId() == null) {
            // 设置执行器Id
            Assert.isTrue(!StringUtils.isEmpty(jobInfo.getExecutorCode()), "error.data_invalid");
            ExecutorDO executor = (ExecutorDO) this.executorMapper.selectOne(new ExecutorDO().builder().executorCode(jobInfo.getExecutorCode()).build());
            if (executor == null) {
                throw new RrException("找不到执行器:" + jobInfo.getExecutorCode());
            }
            jobInfo.setExecutorId(executor.getExecutorId());
        }

        // 非周期性，只执行一次
        if (Objects.equals(jobInfo.getCycleFlag(), 0)) {
            jobInfo.setJobCron((String) null);
        }
        jobInfoMapper.insertSelective(jobInfo);
        // 加入到程序内存中
        this.jobService.addJob(jobInfo);
        return jobInfo;
    }

    @Override
    public JobInfoDO createJobForDate(JobInfoDTO jobInfoDTO) {
        JobInfoDO jobInfo = new JobInfoDO();
        BeanUtils.copyProperties(jobInfoDTO,jobInfo);
        jobInfo.setJobCron(DateUtils.getCron(jobInfoDTO.getJobCronDate()));

        if (jobInfo.getExecutorId() == null) {
            // 设置执行器Id
            Assert.isTrue(!StringUtils.isEmpty(jobInfo.getExecutorCode()), "error.data_invalid");
            ExecutorDO executor = (ExecutorDO) this.executorMapper.selectOne(new ExecutorDO().builder().executorCode(jobInfo.getExecutorCode()).build());
            if (executor == null) {
                throw new RrException("找不到执行器:" + jobInfo.getExecutorCode());
            }
            jobInfo.setExecutorId(executor.getExecutorId());
        }

        // 非周期性，只执行一次
        if (Objects.equals(jobInfo.getCycleFlag(), 0)) {
            jobInfo.setJobCron((String) null);
        }
        jobInfoMapper.insertSelective(jobInfo);
        // 加入到程序内存中
        this.jobService.addJob(jobInfo);
        return jobInfo;
    }

    @Override
    public List<JobInfoDO> list() {
        List<JobInfoDO> jobInfoDOS = jobInfoMapper.selectAll();
        return jobInfoDOS;
    }

    @Async("commonAsyncTaskExecutor")
    @Override
    public void initJob() {
        // 查询数据库表中的定时任务
        List<JobInfoDO> jobInfoDOList = jobInfoMapper.selectAll();
        jobInfoDOList.forEach(jobInfo -> {
            // 获取内存中的任务Job的状态
            String status = jobService.getJobStatus(jobInfo.getJobId());
            if(Objects.equals("NONE",status)){
                // 内存中Job没有运行，添加到内存中
                this.jobService.addJob(jobInfo);
            }
        });
    }


}
