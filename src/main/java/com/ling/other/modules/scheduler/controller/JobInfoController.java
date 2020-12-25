package com.ling.other.modules.scheduler.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.scheduler.databoject.JobInfoDO;
import com.ling.other.modules.scheduler.dto.JobInfoDTO;
import com.ling.other.modules.scheduler.service.JobInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author zhangling
 * @since 2020/9/3 9:52
 */

@Api(tags = "定时任务zero")
@RestController
@RequestMapping({"/job-info"})
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;

    @ApiOperation("任务列表")
    @GetMapping
    public CommonResult<List<JobInfoDO>> list(){
        List<JobInfoDO> list = jobInfoService.list();
        return CommonResult.success(list);
    }
    //
    //{
    //    "createTime":"",
    //    "cycleFlag":1,
    //    "description":"测试任务",
    //    "endDate":"",
    //    "executorCode":"TEST_EXECUTOR",
    //    "executorStrategy":"sdf",
    //    "failStrategy":"sdf",
    //    "glueType":"",
    //    "jobCode":"TEST_JOB",
    //    "jobCron":"5 0 0 * * ? ",
    //    "jobHandler":"",
    //    "jobId":0,
    //    "jobParam":"",
    //    "startDate":"",
    //    "strategyParam":"",
    //    "updateTime":""
    //}
    @ApiOperation("任务创建")
    @PostMapping
    public CommonResult<JobInfoDO> createJob(@RequestBody JobInfoDO jobInfo) {
        JobInfoDO job = jobInfoService.createJob(jobInfo);
        return CommonResult.success(job);
    }

    @ApiOperation("任务创建_根据日期")
    @PostMapping("/date")
    public CommonResult<JobInfoDO> createJobForDate(@RequestBody JobInfoDTO jobInfoDTO) {
        JobInfoDO job = jobInfoService.createJobForDate(jobInfoDTO);
        return CommonResult.success(job);
    }




    /**
     * 10秒钟后执行
     * @return
     */
    @GetMapping("/test")
    public CommonResult addJob(){

        SchedulerFactory sf = new StdSchedulerFactory();

        try {
            Scheduler scheduler = sf.getScheduler();
            JobDetail job = newJob(Test.class)
                    .withIdentity("statefulJob1", "group1")
                    .build();
            Date startTime = new Date();
            long l = startTime.getTime() + 300000;
            Date no = new Date(l);
            SimpleTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(no)
                    .withSchedule(simpleSchedule()
                                    .withIntervalInSeconds(10)
                            //.repeatForever()
                    )
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return CommonResult.success();
    }
}
