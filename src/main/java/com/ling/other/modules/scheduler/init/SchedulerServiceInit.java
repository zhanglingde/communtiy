package com.ling.other.modules.scheduler.init;

import com.ling.other.modules.scheduler.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 任务初始化：初始化数据库表中的定时任务
 * @author zhangling
 * @since 2020/9/10 14:46
 */
@Component
public class SchedulerServiceInit implements CommandLineRunner {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void run(String... args) throws Exception {
        jobInfoService.initJob();
    }
}
