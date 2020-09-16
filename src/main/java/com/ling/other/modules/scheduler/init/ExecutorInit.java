package com.ling.other.modules.scheduler.init;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.aliyun.openservices.shade.org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ling.other.common.annotation.JobHandler;
import com.ling.other.common.utils.SpringContextUtils;
import com.ling.other.modules.scheduler.config.SchedulerConfig;
import com.ling.other.modules.scheduler.job.JobRegistry;
import com.ling.other.modules.scheduler.service.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zhangling
 * @since 2020/9/3 15:23
 */
@Component
public class ExecutorInit implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorInit.class);

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    @Autowired
    private SchedulerConfig schedulerConfig;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("执行器初始化...");

        // 扫描JobHandler注解
        scanJobHandler();
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("scheduler-register-runner-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), threadFactory);
        executor.execute(new ExecutorInit.Registry());
    }

    class Registry implements Runnable{

        Registry() {
        }

        @Override
        public void run() {
            if (StringUtils.isBlank(ExecutorInit.this.schedulerConfig.getExecutorCode())) {
                ExecutorInit.logger.error("Can not find executorCode, please specify the executor code or turn off auto registration!");
            }else if (ExecutorInit.this.schedulerConfig.isAutoRegister()) {
                int time = 1;
                while(time <= schedulerConfig.getRetryTime()){
                    ++time;
                }
            }
        }
    }

    private void scanJobHandler() {
        Map<String, Object> map = SpringContextUtils.getBeansWithAnnotation(JobHandler.class);
        for (Object service : map.values()) {
            // service为JobHandler注解标注的bean
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
