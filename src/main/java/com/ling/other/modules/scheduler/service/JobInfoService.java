package com.ling.other.modules.scheduler.service;

import com.ling.other.modules.scheduler.databoject.JobInfoDO;

import java.util.List;

/**
 * 定时任务业务层
 * @author zhangling
 * @since 2020/9/3 9:53
 */
public interface JobInfoService {

    /**
     * 任务创建
     * @param jobInfo
     */
    JobInfoDO createJob(JobInfoDO jobInfo);

    /**
     * 任务列表
     * @return
     */
    List<JobInfoDO> list();

    /**
     * 初始化任务
     */
    void initJob();
}
