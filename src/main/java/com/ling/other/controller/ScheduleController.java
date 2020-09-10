package com.ling.other.controller;


import com.ling.other.entity.SysJobPO;
import com.ling.other.common.utils.CommonResult;
import com.ling.other.entity.Task;
import com.ling.other.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/9/1 16:15
 */
@RestController
@RequestMapping("/schedule")
@Api(tags = "定时任务")
public class ScheduleController {


    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("定时任务列表")
    @GetMapping("/list")
    public CommonResult<List<Task>> list(){

        return CommonResult.success(scheduleService.list());
    }

    @PostMapping("/create")
    @ApiOperation("新增")
    public CommonResult create(@RequestBody SysJobPO sysJob){

        scheduleService.create(sysJob);
        return CommonResult.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改定时任务")
    public CommonResult update(@RequestBody Task task){

        scheduleService.update(task);
        return CommonResult.success();
    }

    @ApiOperation("删除定时任务")
    @GetMapping("/delete/{jobId}")
    public CommonResult delete(@PathVariable Integer jobId){
        scheduleService.delete(jobId);
        return CommonResult.success();
    }

    @ApiOperation("启动/停止")
    @PostMapping("/start/{jobId}")
    public CommonResult startOrStop(@PathVariable Integer jobId){
        scheduleService.start(jobId);
        return CommonResult.success();
    }
}
