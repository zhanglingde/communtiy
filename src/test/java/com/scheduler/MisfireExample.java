package com.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zhangling
 * @since 2020/9/10 13:13
 */
public class MisfireExample {
    public static void main(String[] args) {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler scheduler = sf.getScheduler();

            JobDetail job = newJob(StatefulDumbJob.class)
                    .withIdentity("statefulJob1", "group1")
                    .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                    .build();
            Date startTime = new Date();
            SimpleTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            //.repeatForever()
                    )
                    .build();

            scheduler.scheduleJob(job, trigger);

            job = newJob(StatefulDumbJob.class)
                    .withIdentity("statefulJob2", "group1")
                    .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                    .build();
            trigger = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startAt(startTime)
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever()
                            .withMisfireHandlingInstructionNowWithExistingCount()) // set misfire instruction
                    .build();
            //scheduler.scheduleJob(job, trigger);

            scheduler.start();
            //Thread.sleep(600L * 1000L);
            //scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
