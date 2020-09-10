package com.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author zhangling
 * @since 2020/9/9 14:38
 */
public class QuartzHelloWorld {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();


            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                    // 身份标识 JobKey jobKey = context.getJobDetail().getKey();
                    .withIdentity("job1","group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1","group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .forJob(jobDetail)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            Thread.sleep(60000);
            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
