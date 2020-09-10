package com.scheduler.exception;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangling
 * @since 2020/9/10 13:31
 */
public class BadJob1 implements Job {
    private static final Logger logger = LoggerFactory.getLogger(BadJob1.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            int zero = 0;
            int calculation = 4815 / zero;
        }
        catch (Exception e) {
            logger.info("--- Error in job!");
            JobExecutionException e2 = new JobExecutionException(e);
            // this job will refire immediately
            e2.refireImmediately();
            throw e2;
        }
    }
}
