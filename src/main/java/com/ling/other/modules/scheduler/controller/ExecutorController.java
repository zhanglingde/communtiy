package com.ling.other.modules.scheduler.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.entity.ExecutorDO;
import com.ling.other.modules.scheduler.service.ExecutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 执行器
 * @author zhangling
 * @since 2020/9/3 13:53
 */
@RestController
@RequestMapping("/executors")
@Api(tags = "执行器")
public class ExecutorController {

    @Autowired
    private ExecutorService executorService;

    @ApiOperation("创建执行器")
    @PostMapping
    public CommonResult<ExecutorDO> createExecutor(@RequestBody ExecutorDO executor) {
        ExecutorDO executorDO =  this.executorService.createExecutor(executor);
        return CommonResult.success();
    }
}
