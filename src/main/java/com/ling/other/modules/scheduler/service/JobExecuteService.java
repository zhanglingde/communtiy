package com.ling.other.modules.scheduler.service;

import com.ling.other.modules.scheduler.dto.JobDataDTO;
import com.ling.other.modules.scheduler.dto.JobInfoDTO;

/**
 * @author zhangling
 * @since 2020/12/28 13:49
 */
public interface JobExecuteService {

    /**
     * 执行调度任务
     * @param jobInfoDTO
     * @return
     */
    String jobExecute(JobDataDTO jobDataDTO);
}
