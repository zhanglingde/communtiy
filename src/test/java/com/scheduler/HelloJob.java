package com.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author zhangling
 * @since 2020/9/9 14:41
 */
public class HelloJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();
        logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
        System.out.println("定时任务测试");

        String name = context.getJobDetail().getKey().getName();
    }
}
