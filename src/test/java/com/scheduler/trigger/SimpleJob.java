package com.scheduler.trigger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 *
 * @author zhangling
 * @since 2020/9/10 10:18
 */
public class SimpleJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    /**
     * 每个定时任务执行一次就会调用该方法一次
     * @param context   定时任务容器（相当于SpringIoc容器ApplicationContext），可获得定时任务内容和触发器
     * @throws JobExecutionException    异常
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
    }
}
