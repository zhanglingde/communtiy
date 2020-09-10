package com.ling.other.modules.scheduler.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.scheduler.databoject.JobInfoDO;
import com.ling.other.modules.scheduler.service.JobInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/9/3 9:52
 */

@Api(tags = "定时任务")
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

    @ApiOperation("任务创建")
    @PostMapping
    public CommonResult<JobInfoDO> createJob(@RequestBody JobInfoDO jobInfo) {
        JobInfoDO job = jobInfoService.createJob(jobInfo);
        return CommonResult.success(job);
    }
}
