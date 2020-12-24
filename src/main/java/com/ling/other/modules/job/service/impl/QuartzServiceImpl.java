package com.ling.other.modules.job.service.impl;

import com.ling.other.modules.job.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * quartz工具类
 * @author nanziyu
 * @date 2020/11/11 13:06
 */
@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Override
    public void addJob(Scheduler scheduler, String jobName, Class<? extends Job> cls, String time) {

    }

    @Override
    public void addJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                       String time, Map<String,String> param) {

        try {
            // JobDetail jobDetail = new JobDetail(jobName, jobGroupName,
            // jobClass);
            JobDetail jobDetail =null;
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).withDescription("");
            if (param!=null){
                param.forEach((key,value)->jobBuilder.usingJobData(key,value));
            }
            jobDetail = jobBuilder.build();
            // 任务名，任务组，任务执行类
            // MethodInvokingJobDetailFactoryBean jobDetail = new
            // MethodInvokingJobDetailFactoryBean();
            // jobDetail.setConcurrent(true);
            // jobDetail.setName(jobName);
            // jobDetail.setGroup(jobGroupName);
            // jobDetail.setTargetClass(jobClass);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionDoNothing();

            // 触发器
            // CronTrigger trigger = new CronTrigger(triggerName,
            // triggerGroupName);
            // 触发器名,触发器组
            // CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
            // trigger.setCronExpression(time);// 触发器时间设定
            // trigger.setName(triggerName);
            // trigger.setJobDetail(jobDetail);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withDescription("").withSchedule(scheduleBuilder)
                    .startNow().build();

            scheduler.scheduleJob(jobDetail, cronTrigger);
            log.debug("添加定时任务成功");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteJob(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.getJobDetail(jobKey) != null) {
                scheduler.deleteJob(jobKey);
            }
            // log.info("Delete schedule job {}-{} success", jobGroupName,
            // jobName);
            log.info("Delete schedule job {}-{} success", jobGroupName, jobName);
        } catch (Exception e) {
            // log.info("Delete schedule job failed" + e.toString());
            log.info("Delete schedule job failed" + e.toString());
            throw new RuntimeException("Delete job failed", e);
        }

    }

    @Override
    public void deleteJobList(Scheduler scheduler, List<String> jobNameList, String jobGroupName) {
        try {
            List<JobKey> jobKeys = new ArrayList<>();
            jobNameList.stream().forEach(jobName -> jobKeys.add(JobKey.jobKey(jobName, jobGroupName)));
            scheduler.deleteJobs(jobKeys);
        } catch (Exception e) {
            // log.info("Delete schedule job failed" + e.toString());
            log.info("Delete schedule job failed" + e.toString());
            throw new RuntimeException("Delete job failed", e);
        }
    }

    @Override
    public void modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String time) {
        try {

            if (StringUtils.isEmpty(time)) {
                return;
            }
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 生成新触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionDoNothing();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withDescription("")
                    .withSchedule(scheduleBuilder).startNow().build();

            // 修改任务触发时间
            scheduler.rescheduleJob(triggerKey, cronTrigger);


        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    @Override
    public void modifyJobTime(Scheduler scheduler, String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              Class<? extends Job> jobClass, String time, Map<String,String> param) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            if (scheduler.getJobDetail(jobKey) != null) {
                modifyJobTime(scheduler, triggerName, triggerGroupName, time);
            } else {
                addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, time,param);
            }
        } catch (Exception e) {
            log.info("modify schedule job failed" + e.toString());
            throw new RuntimeException("modify job failed", e);
        }

    }

    @Override
    public void pauseJob(Scheduler scheduler, String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.getJobDetail(jobKey) != null) {
                scheduler.pauseJob(jobKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }

    }

    @Override
    public void resumeJob(Scheduler scheduler, String jobName, String jobGroupName) {

        try {

            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

            if (scheduler.getJobDetail(jobKey) != null) {
                scheduler.resumeJob(jobKey);
            }

        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    /**
     * 校验corn表达式的准确性
     *
     * @param cronExpression
     * @return
     */
    @Override
    public Boolean isValidCornExpression(String cronExpression) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            Date now = new Date();
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && !now.after(date);
        } catch (Exception e) {
            log.error("[TaskUtils.isValidExpression]:failed. throw ex:", e);
        }
        return false;
    }
}


