package com.scheduler.param;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 *
 * 有参数的传参job
 * @author zhangling
 * @since 2020/9/10 11:06
 */
public class JobStateExample {
    public static void main(String[] args) {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler scheduler = sf.getScheduler();

            //Job #1 is scheduled to run every 10 seconds, for a total of five times:
            JobDetail job1 = newJob(ColorJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            Date startTime = new Date();
            SimpleTrigger trigger1 = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            .withRepeatCount(4))
                    .build();

            //Job #1 is passed in two job parameters. One is a favorite color, with a value of “Green”. The other is an execution count, which is initialized with a value of 1.
            job1.getJobDataMap().put("favoriteColor", "Green");
            job1.getJobDataMap().put("count", 1);
            scheduler.scheduleJob(job1, trigger1);

            //  Job #2 is also scheduled to run every 10 seconds, for a total of five times:
            JobDetail job2 = newJob(ColorJob.class)
                    .withIdentity("job2", "group1")
                    .build();
            SimpleTrigger trigger2 = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            .withRepeatCount(4))
                    .build();
            // Job #2 is also passed in two job parameters. One is a favorite color, with a value of “Red”. The other is an execution count, which is initialized with a value of 1.
            job2.getJobDataMap().put("favoriteColor", "Red");
            job2.getJobDataMap().put("count", 1);
            scheduler.scheduleJob(job2, trigger2);

            scheduler.start();
            Thread.sleep(60L * 1000L);
            scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
