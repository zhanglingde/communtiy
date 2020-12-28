package com.ling.other.modules.scheduler.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.scheduler.databoject.ExecutorDO;
import com.ling.other.modules.scheduler.service.ExecutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 执行器
 * @author zhangling
 * @since 2020/9/3 13:53
 */
@RestController
@RequestMapping("/executors")
@Api(tags = "执行器")
public class ExecutorController {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorController.class);

    @Autowired
    private ExecutorService executorService;


    /**
     * {
     * 	"addressList": "",
     * 	"createTime": "",
     * 	"executorCode": "TEST_EXECUTOR",
     * 	"executorName": "测试执行器",
     * 	"executorType": 0,
     * 	"orderSeq": 0,
     * 	"status": "开启",
     * 	"updateTime": ""
     * }
     * @param executor
     * @return
     */

    @ApiOperation("创建执行器")
    @PostMapping
    public CommonResult<ExecutorDO> createExecutor(@RequestBody ExecutorDO executor) {
        ExecutorDO executorDO =  this.executorService.createExecutor(executor);
        return CommonResult.success();
    }


    @ApiOperation("客户端刷新执行器")
    @PostMapping({"/refresh"})
    public CommonResult<String> refreshExecutor( @RequestParam String executorCode, @RequestParam String serverName) {
        return CommonResult.success(this.executorService.refreshExecutor(executorCode));
    }
    @GetMapping({"/test"})
    public CommonResult test(){

        logger.debug("debug日志");
        logger.info("info日志");
        return CommonResult.success();
    }
}
