package com.ling.other.modules.scheduler.service;

import com.ling.other.modules.scheduler.databoject.JobInfoDO;

/**
 *
 *
 * @author zhangling
 * @since 2020/9/3 10:07
 */
public interface IJobService {

    /**
     * 添加定时任务到程序内存中
     * @param jobInfo 任务信息
     */
    void addJob(JobInfoDO jobInfo);

    /**
     * 获取job的状态
     * @param jobId triggerKey
     * @return triggerStatus
     */
    String getJobStatus(Long jobId);
}
