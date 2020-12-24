package com.ling.other.modules.job;

import com.ling.other.common.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Api("定时任务1")
public class Controller {

    @Autowired
    QuartzManage quartzManage;


    @ApiOperation("添加定时任务")
    @GetMapping("/addJob")
    public CommonResult<String> addJob(){
        Date now = null;
        try {
            now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-24 22:28:10");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String job = quartzManage.addJob("测试Job", "2", now, NoticeSendJob.class);
        return CommonResult.success(job);
    }

}
