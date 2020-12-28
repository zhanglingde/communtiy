package com.ling.other.modules.scheduler.job;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.ling.other.common.constants.BaseConstants;
import com.ling.other.mapper.JobLogMapper;
import com.ling.other.modules.scheduler.databoject.JobLogDO;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * job其他操作
 * 定时任务执行的时候，添加日志等操作
 *
 * @author zhangling
 * @since 2020/9/3 13:28
 */
public class MyJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MyJob.class);
    private Integer retryNumber = 3;
    private Integer retry = 1;
    private String executorStrategy;
    private String failStrategy;

    @Autowired
    private JobLogMapper jobLogMapper;

    public MyJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("定时任务开始执行");

        String triggerName = context.getTrigger().getKey().getName();
        Long jobId = Long.valueOf(context.getJobDetail().getKey().getName());
        String group = context.getJobDetail().getKey().getGroup();
        logger.info("Scheduler Job Start JobId : {} , group : {}", jobId, group);
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();


        Long executorId = jobDataMap.getLongValue("executorId");
        Integer serial = jobDataMap.get("serial") != null && StringUtils.isBlank(String.valueOf(jobDataMap.get("serial"))) ? Integer.valueOf(String.valueOf(jobDataMap.get("serial"))) : BaseConstants.Flag.YES;

        // TODO job加锁
        //if(Objects.equals(serial, BaseConstants.Flag.YES) && !JobLock.add)
        String jobCode = jobDataMap.getString("jobCode");
        String jobType = jobDataMap.getString("jobType");
        String jobHandler = jobDataMap.getString("jobHandler");
        this.failStrategy = jobDataMap.getString("failStrategy");
        this.executorStrategy = jobDataMap.getString("executorStrategy");
        if (jobDataMap.containsKey("retryNumber")) {
            this.retryNumber = Integer.parseInt(String.valueOf(jobDataMap.get("retryNumber")));
        }
        logger.info("定时任务JobCode: {} 添加日志", jobCode);
        // 添加执行日志
        Date now = new Date();
        JobLogDO jobLog = JobLogDO.builder().jobId(jobId)
                .executorId(executorId)
                .startTime(now)
                .jobResult("SUCCESS")
                .clientResult("DOING")
                .build();

        jobLogMapper.insertSelective(jobLog);

        Set<String> errorList = new HashSet<>();
        // TODO 邮件发送job运行失败信息

    }
}
