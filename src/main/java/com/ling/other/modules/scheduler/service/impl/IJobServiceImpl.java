package com.ling.other.modules.scheduler.service.impl;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.ling.other.common.exception.RrException;
import com.ling.other.entity.ExecutorDO;
import com.ling.other.modules.scheduler.databoject.JobInfoDO;
import com.ling.other.mapper.ExecutorMapper;
import com.ling.other.modules.scheduler.job.MyJob;
import com.ling.other.modules.scheduler.service.IJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author zhangling
 * @since 2020/9/3 10:07
 */
@Service
public class IJobServiceImpl implements IJobService {

    private static final Logger logger = LoggerFactory.getLogger(IJobServiceImpl.class);

    @Autowired
    private ExecutorMapper executorMapper;


    @Autowired
    private Scheduler scheduler;


    @Override
    public void addJob(JobInfoDO jobInfo) {
        ExecutorDO executor = (ExecutorDO) this.executorMapper.selectByPrimaryKey(jobInfo.getExecutorId());
        if (executor == null) {
            throw new RrException("找不到执行器:"+jobInfo.getExecutorId());
        }

        try {
            //JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withDescription(jobInfo.getDescription()).withIdentity(String.valueOf(jobInfo.getJobId()), 1)).usingJobData(this.getMap(jobInfo)).build();
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withDescription(jobInfo.getDescription())
                    .withIdentity(String.valueOf(jobInfo.getJobId()), "jobDetails")
                    .usingJobData(this.getMap(jobInfo))
                    .build();
            Trigger trigger = this.buildTrigger(jobInfo);
            if (trigger == null) {
                throw new RrException("error.create_job");
            }
            this.scheduler.scheduleJob(jobDetail, trigger);
            this.scheduler.start();
            logger.debug("-----------------  add job success, jobId : {}  ", jobInfo.getJobId());
        } catch (Exception var6) {
            throw new RrException("error.quartz.add", var6);
        }
    }

    @Override
    public String getJobStatus(Long jobId) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(String.valueOf(jobId), "triggers");
            return this.scheduler.getTriggerState(triggerKey).name();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RrException("error.data_invalid",e);
        }
    }

    /**
     * 定时任务容器中任务参数
     * @param jobInfo 传参实体
     * @return
     */
    private JobDataMap getMap(JobInfoDO jobInfo) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobCode", jobInfo.getJobCode());
        jobDataMap.put("executorId", jobInfo.getExecutorId());
        jobDataMap.put("jobType", jobInfo.getGlueType());
        jobDataMap.put("jobHandler", jobInfo.getJobHandler());
        jobDataMap.put("failStrategy", jobInfo.getFailStrategy());
        jobDataMap.put("executorStrategy", jobInfo.getExecutorStrategy());
        if (jobInfo.getStartDate() != null) {
            jobDataMap.put("startDate", jobInfo.getStartDate());
        }

        if (jobInfo.getEndDate() != null) {
            jobDataMap.put("endDate", jobInfo.getEndDate());
        }

        jobDataMap.put("param", jobInfo.getJobParam());
        //if (StringUtils.isNotBlank(jobInfo.getStrategyParam())) {
            //Map<String, Object> strategyParam = (Map)this.objectMapper.readValue(jobInfo.getStrategyParam(), new TypeReference<Map<String, Object>>() {
            //});
            //jobDataMap.putAll(strategyParam);
        //}


        return jobDataMap;
    }

    /**
     * 构建定时任务触发器
     * @param jobInfo 根据周期性，开始时间和结束时间 构建不同触发器
     * @return  定时任务触发器
     */
    private Trigger buildTrigger(JobInfoDO jobInfo) {
        TriggerKey triggerKey = TriggerKey.triggerKey(String.valueOf(jobInfo.getJobId()), "triggers");
        Trigger trigger = null;
        if (Objects.equals(jobInfo.getCycleFlag(), 1)) {
            // 周期性定时任务
            String cron = jobInfo.getJobCron();
            if (StringUtils.isEmpty(cron)) {
                cron = "0 0 0 1 * ? 2100";
            }
            // 构建cron表达式  misfire：任务错过后的处理策略
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing();
            if (jobInfo.getStartDate() != null && jobInfo.getEndDate() != null) {
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).startAt(jobInfo.getStartDate()).endAt(jobInfo.getEndDate()).build();
            }

            if (jobInfo.getStartDate() != null && jobInfo.getEndDate() == null) {
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).startAt(jobInfo.getStartDate()).build();
            }

            long currentTime;
            if (jobInfo.getStartDate() == null && jobInfo.getEndDate() != null) {
                currentTime = System.currentTimeMillis() + 30000L;
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).startAt(new Date(currentTime)).endAt(jobInfo.getEndDate()).build();
            }

            if (jobInfo.getStartDate() == null && jobInfo.getEndDate() == null) {
                currentTime = System.currentTimeMillis() + 30000L;
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).startAt(new Date(currentTime)).build();
            }
        } else if (jobInfo.getStartDate() == null) {
            // 非周期性任务，且开始时间为null
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).startNow().build();
        } else {
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).startAt(jobInfo.getStartDate()).build();
        }

        return trigger;
    }
}
