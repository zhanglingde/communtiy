package com.ling.other.modules.scheduler.init;

import com.ling.other.common.annotation.JobHandler;
import com.ling.other.common.utils.SpringContextUtils;
import com.ling.other.modules.scheduler.job.JobRegistry;
import com.ling.other.modules.scheduler.service.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangling
 * @since 2020/9/3 15:23
 */
@Component
public class ExecutorInit implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorInit.class);

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("执行器初始化...");

        // 扫描JobHandler注解
        scanJobHandler();

    }

    private void scanJobHandler() {
        Map<String, Object> map = SpringContextUtils.getBeansWithAnnotation(JobHandler.class);
        for (Object service : map.values()) {
            if (service instanceof IJobHandler) {
                JobHandler jobHandler = service.getClass().getAnnotation(JobHandler.class);
                if (ObjectUtils.isEmpty(jobHandler)) {
                    logger.debug("could not get target bean , jobHandler : {}", service);
                } else {
                    JobRegistry.addJobHandler(jobHandler.value(), service);
                }
            }
        }
    }
}
