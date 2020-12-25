package com.ling.other.modules.schedule_java.service;

import com.ling.other.entity.Task;
import com.ling.other.modules.schedule_java.config.SysJobPO;
import com.ling.other.modules.schedule_java.dto.JobDTO;

import java.util.List;

/**
 * 定时任务
 * @author zhangling
 * @since 2020/9/2 9:51
 */
public interface ScheduleService {

    /**
     * 新增定时任务
     * @param sysJob
     */
    void create(SysJobPO sysJob);


    /**
     * 编辑定时任务
     * @param task
     */
    void update(Task task);

    /**
     * 删除定时任务
     * @param jobId
     */
    void delete(Integer jobId);


    List<Task> list();

    /**
     * 定时任务启动/停止状态切换
     * @param jobId
     */
    void start(Integer jobId);

    /**
     * 根据前端传的日期设置定时任务
     * @param jobDTO
     */
    void createForDate(JobDTO jobDTO);
}
