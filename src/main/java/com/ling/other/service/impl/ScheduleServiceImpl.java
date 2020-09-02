package com.ling.other.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ling.other.common.constants.BaseConstants;
import com.ling.other.common.exception.RrException;
import com.ling.other.common.schedule.config.CronTaskRegistrar;
import com.ling.other.common.schedule.config.SchedulingRunnable;
import com.ling.other.entity.SysJobPO;
import com.ling.other.entity.Task;
import com.ling.other.mapper.TaskMapper;
import com.ling.other.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
