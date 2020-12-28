package com.ling.other.modules.schedule_java.init;


import com.ling.other.common.constants.BaseConstants;
import com.ling.other.mapper.TaskMapper;
import com.ling.other.modules.schedule_java.config.CronTaskRegistrar;
import com.ling.other.modules.schedule_java.config.SchedulingRunnable;
import com.ling.other.modules.schedule_java.config.SysJobPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * CommandLineRunner接口实现类，
 * 当Spring Boot项目启动完成后，加载数据库里状态为正常的定时任务
 */
@Service
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private TaskMapper demoTestDao;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务

        List<SysJobPO> jobList = demoTestDao.getSysJobListByStatus(BaseConstants.TaskStatus.NORMAL);
        if (!CollectionUtils.isEmpty(jobList)) {
            for (SysJobPO job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }

            logger.info("定时任务已加载完毕...");
        }
    }
}