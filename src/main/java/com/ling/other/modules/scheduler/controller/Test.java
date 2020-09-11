package com.ling.other.modules.scheduler.controller;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zhangling
 * @since 2020/9/11 9:17
 */
public class Test implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("单次测试");
    }
}
