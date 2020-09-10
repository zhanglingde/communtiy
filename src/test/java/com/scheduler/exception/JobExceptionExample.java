package com.scheduler.exception;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zhangling
 * @since 2020/9/10 13:53
 */
public class JobExceptionExample {

    public static void main(String[] args) {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler scheduler = sf.getScheduler();

            // Job #1 is scheduled to run every 3 seconds indefinitely. This job will fire BadJob1.
            JobDetail job = newJob(BadJob1.class)
                    .withIdentity("badJob1", "group1")
                    .build();
            Date startTime = new Date();
            SimpleTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever())
                    .build();

            Date ft = scheduler.scheduleJob(job, trigger);


            //Job #2 is scheduled to run every 3 seconds indefinitely. This job will fire BadJob2.
            job = newJob(BadJob2.class)
                    .withIdentity("badJob2", "group1")
                    .build();
            trigger = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever())
                    .build();
            ft = scheduler.scheduleJob(job, trigger);

            scheduler.start();
            Thread.sleep(60L * 1000L);
            scheduler.shutdown(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
