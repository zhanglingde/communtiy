package com.ling.other.modules.job;

import com.ling.other.modules.job.constant.JobConstant;
import com.ling.other.modules.job.service.NoticeSendJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 消息通知的定时任务
 * @author nanziyu
 * @date 2020/11/13 13:35
 */
@Slf4j
public class NoticeSendJob extends QuartzJobBean {

    @Autowired
    private NoticeSendJobService noticeSendJobService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("----------------------->  消息通知开始");
        System.out.println("消息通知开始----");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Object id = jobDataMap.get(JobConstant.JOBPARAM_NAME);
        noticeSendJobService.sendNotice((String) id);
    }
}