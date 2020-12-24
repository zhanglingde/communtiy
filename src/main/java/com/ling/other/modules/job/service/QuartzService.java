package com.ling.other.modules.job.service;

import org.quartz.Job;
import org.quartz.Scheduler;

import java.util.List;
import java.util.Map;


/**
 * quartz工具类
 * @author nanziyu
 * @date 2020/11/19 13:06
 */
public interface QuartzService {

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * 
     * @param scheduler
     *            调度器
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置
     */
    void addJob(Scheduler scheduler, String jobName, Class<? extends Job> cls, String time);

    /**
     * 添加一个定时任务
     * 
     * @param scheduler
     *            调度器
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务
     * @param time
     *            时间设置
     */
    void addJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                String time, Map<String,String> param);

    /**
     * 删除定时任务
     *
     * @param scheduler
     *            调度器
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     */
    void deleteJob(Scheduler scheduler, String jobName, String jobGroupName);

    /**
     * 批量删除定时任务
     *
     * @param scheduler
     *            调度器
     * @param jobNameList
     *            任务名列表
     * @param jobGroupName
     *            任务组名
     */
    void deleteJobList(Scheduler scheduler, List<String> jobNameList, String jobGroupName);

    /**
     * 修改任务的触发时间
     *
     * @param scheduler
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    void modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String time);

    /**
     * 修改任务时间
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobClass
     * @param time
     * @param param
     */
    void modifyJobTime(Scheduler scheduler, String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                       String time, Map<String,String> param);

    /**
     * 暂停任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     *
     */
    void pauseJob(Scheduler scheduler, String jobName, String jobGroupName);

    /**
     * 恢复任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     *
     */
    void resumeJob(Scheduler scheduler, String jobName, String jobGroupName);

    /**
     *
     * @param cronExpression
     * @return
     */
    Boolean isValidCornExpression(String cronExpression);
}
