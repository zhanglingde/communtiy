package com.ling.other.modules.schedule_java.service.impl;

import com.ling.other.common.constants.BaseConstants;
import com.ling.other.common.exception.RrException;

import com.ling.other.common.utils.DateUtils;
import com.ling.other.entity.Task;
import com.ling.other.mapper.TaskMapper;
import com.ling.other.modules.schedule_java.config.CronTaskRegistrar;
import com.ling.other.modules.schedule_java.config.SchedulingRunnable;
import com.ling.other.modules.schedule_java.config.SysJobPO;
import com.ling.other.modules.schedule_java.dto.JobDTO;
import com.ling.other.modules.schedule_java.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/9/2 9:54
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysJobPO sysJob) {
        boolean success = taskMapper.addSysJob(sysJob);
        if (!success){
            throw new RrException("新增定时任务失败");
        } else {
            if (sysJob.getJobStatus().equals(1)) {
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createForDate(JobDTO jobDTO) {

        SysJobPO sysJob = SysJobPO.builder()
                .beanName(jobDTO.getBeanName())
                .methodName(jobDTO.getMethodName())
                .methodParams(jobDTO.getMethodParams())
                .cronExpression(DateUtils.getCron(jobDTO.getExecuteDate()))
                .jobStatus(jobDTO.getJobStatus())
                .remark(jobDTO.getRemark())
                .build();

        boolean success = taskMapper.addSysJob(sysJob);
        if (!success){
            throw new RrException("新增定时任务失败");
        } else {
            if (sysJob.getJobStatus().equals(1)) {
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Task taskDTO) {
        // 查询已存在表中的定时任务
        Task job = taskMapper.selectByPrimaryKey(taskDTO.getJobId());
        boolean success = taskMapper.updateTask(taskDTO);
        if (!success){
            throw new RrException("编辑失败");
        } else {
            //先移除再添加
            if (job.getJobStatus().equals(BaseConstants.TaskStatus.NORMAL)) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
            // 在程序中添加修改的新的定时任务
            if (taskDTO.getJobStatus().equals(BaseConstants.TaskStatus.NORMAL)) {
                SchedulingRunnable task = new SchedulingRunnable(taskDTO.getBeanName(), taskDTO.getMethodName(), taskDTO.getMethodParams());
                cronTaskRegistrar.addCronTask(task, taskDTO.getCronExpression());
            }
        }
    }

    @Override
    public void delete(Integer jobId) {
        Task existJob = taskMapper.selectByPrimaryKey(jobId);
        int i = taskMapper.deleteByPrimaryKey(jobId);
        if (i == 0)
            throw new RrException("删除失败");
        else{
            if (existJob.getJobStatus().equals(BaseConstants.TaskStatus.NORMAL)) {
                SchedulingRunnable task = new SchedulingRunnable(existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
        }
    }

    @Override
    public List<Task> list() {
        List<Task> tasks = taskMapper.selectAll();
        return tasks;
    }

    @Override
    public void start(Integer jobId) {

        Task existJob = taskMapper.selectByPrimaryKey(jobId);
        if (existJob.getJobStatus().equals(BaseConstants.TaskStatus.NORMAL)) {
            // 启动-> 暂停
            SchedulingRunnable task = new SchedulingRunnable(existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);

            taskMapper.updateByPrimaryKeySelective(Task.builder()
                    .jobId(jobId)
                    .jobStatus(BaseConstants.TaskStatus.PAUSE)
                    .build());

        } else {
            SchedulingRunnable task = new SchedulingRunnable(existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, existJob.getCronExpression());

            taskMapper.updateByPrimaryKeySelective(Task.builder()
                    .jobId(jobId)
                    .jobStatus(BaseConstants.TaskStatus.NORMAL)
                    .build());
        }
    }

    //@Scheduled(cron = "0/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void purchaseOrderClosed() {
        System.out.println("cron表达式的定时任务");
    }


}
