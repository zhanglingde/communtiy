package com.ling.other.common.schedule.config;


import com.ling.other.mapper.DemoTestDao;
import com.ling.other.common.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangling
 * @since 2020/9/1 16:15
 */
@RestController
@RequestMapping("/supplier")
@Api(tags = "定时任务")
public class DemoTestController {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    private DemoTestDao demoTestDao;

    @PostMapping("/create")
    @ApiOperation("新增")
    public CommonResult create(@RequestBody SysJobPO sysJob){

        boolean success = demoTestDao.addSysJob(sysJob);
        //boolean success = true;
        if (!success)
            return CommonResult.error("新增失败");
        else {
            if (sysJob.getJobStatus().equals(1)) {
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
        }

        return CommonResult.success();
    }
}
