package com.ling.other.modules.scheduler.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.scheduler.dto.JobDataDTO;
import com.ling.other.modules.scheduler.dto.JobInfoDTO;
import com.ling.other.modules.scheduler.service.JobExecuteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangling
 * @since 2020/12/28 13:46
 */
@RestController
@RequestMapping(value ="/scheduler")
@Api("任务执行控制器")
public class JobExecuteController {

    @Autowired
    private JobExecuteService jobExecuteService;


    @ApiOperation(value = "执行任务")
    @GetMapping("executor")
    public CommonResult<String> runJob(JobDataDTO jobDataDTO){
        return CommonResult.success(jobExecuteService.jobExecute(jobDataDTO));
    }
}
