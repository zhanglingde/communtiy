package com.scheduler.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * cron表达式触发器设置
 * @author zhangling
 * @since 2020/9/10 10:15
 */
public class CronBaseTriggers {
    public static void main(String[] args) {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler scheduler = sf.getScheduler();

            // Job #1 is scheduled to run every 20 seconds
            JobDetail job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            // Job #2 is scheduled to run every other minute, starting at 15 seconds past the minute.
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job2", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 * * * ?"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            //Job #3 is scheduled to every other minute, between 8am and 5pm (17 o’clock).
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job3", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger3", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            // Job #4 is scheduled to run every three minutes but only between 5pm and 11pm
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job4", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger4", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 17-23 * * ?"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            // Job #5 is scheduled to run at 10am on the 1st and 15th days of the month
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job5", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger5", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10am 1,15 * ?"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            // Job #6 is scheduled to run every 30 seconds on Weekdays (Monday through Friday)
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job6", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger6", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * MON-FRI"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            // Job #7 is scheduled to run every 30 seconds on Weekends (Saturday and Sunday)
            job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("job7", "group1")
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger7", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * SAT,SUN"))
                    .build();
            scheduler.scheduleJob(job, trigger);

            scheduler.start();
            Thread.sleep(300L * 1000L);
            scheduler.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
