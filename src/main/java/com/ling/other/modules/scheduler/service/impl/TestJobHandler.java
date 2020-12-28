package com.ling.other.modules.scheduler.service.impl;

import com.ling.other.modules.scheduler.annotation.JobHandler;
import com.ling.other.modules.scheduler.myenum.ReturnT;
import com.ling.other.modules.scheduler.service.IJobHandler;
import com.ling.other.modules.scheduler.utils.SchedulerTool;

import java.util.Map;

/**
 * JobHandler的值为定时任务的JobCode
 * @author zhangling
 * @since 2020/9/3 15:21
 */
@JobHandler("TEST_JOB")
public class TestJobHandler implements IJobHandler {


    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        System.out.println("JobHandler 测试");
        return ReturnT.SUCCESS;
    }
}
