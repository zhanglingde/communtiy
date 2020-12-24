package com.ling.other.modules.job;


import com.ling.other.common.utils.DateUtils;
import com.ling.other.common.utils.keygenerator.KeyGeneratorUtil;
import com.ling.other.modules.job.constant.JobConstant;
import com.ling.other.modules.job.service.QuartzService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化bean
 * @author nanziyu
 * @date 2020/11/11 13:06
 */
@Component
@Lazy(false)
public class QuartzManage {

    @Resource
    private QuartzService quartzService;

    @Autowired
    private Scheduler scheduler;

    /**
     * 初始化任务
     */
    @PostConstruct
    public void init() {
//        quartzService.modifyJobTime(scheduler, "synDayStockJob", "activityJobGroup", "synDayStockJobTrigger", "activityTriggerGroup", NoticeSendJob.class,
//                "0/5 * * * * ?",null);
    }

    /**
     * 增加或者修改一个定时任务
     * @param moudleName
     * @param id
     * @param date
     * @param jobClass
     */
    public String addJob(String moudleName, String id, Date date,Class<? extends Job> jobClass){
        String randomStr = Long.toString(KeyGeneratorUtil.getGenerator().nextKey());
        String nameEnd = moudleName + id;
        String jobName = randomStr+ JobConstant.JOB_NAME+nameEnd;
        String  jobGroup = JobConstant.JOB_GROUP+moudleName;
        String triggerName = randomStr+JobConstant.TRIGGER_NAME + nameEnd;
        String triggerGroup = JobConstant.TRIGGER_GROUP+moudleName;
        String dateCron = DateUtils.getCron(date);

        Map<String,String> param = new HashMap<>();
        param.put(JobConstant.JOBPARAM_NAME,id);
        quartzService.modifyJobTime(scheduler,jobName,jobGroup,triggerName,triggerGroup,jobClass,dateCron,param);
        return  jobName;
    }

    /**
     * 删除一个定时任务
     * @param moudle
     * @param jobName
     * @return
     */
    public  int  deleteJob(String moudle,String jobName){
        quartzService.deleteJob(scheduler,jobName,JobConstant.JOB_GROUP+moudle);
      return  1;
    }
}
