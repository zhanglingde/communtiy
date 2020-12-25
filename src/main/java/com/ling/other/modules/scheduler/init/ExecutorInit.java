package com.ling.other.modules.scheduler.init;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.aliyun.openservices.shade.org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ling.other.common.constants.BaseConstants;
import com.ling.other.common.utils.SpringContextUtils;
import com.ling.other.modules.scheduler.annotation.JobHandler;
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
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 执行器初始化
 * @author zhangling
 * @since 2020/9/3 15:23
 */
@Component
public class ExecutorInit implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorInit.class);

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    @Autowired
    private SchedulerConfig schedulerConfig;

    @Autowired
    com.ling.other.modules.scheduler.service.ExecutorService  executorService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("执行器初始化...");

        // 扫描JobHandler注解
        scanJobHandler();

        // 自定义 线程工厂，可以自定义线程名（阿里Java规范要求自定义线程名，不能使用默认的线程池，方便出错找到）
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("scheduler-register-runner-%d").build();
        // 定义执行定时任务的线程池
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
                    // 重试定时任务，默认5次
                    ++time;
                    // 判断执行器存不存在，不存在新建执行器
                    String result = (String)executorService.refreshExecutor( ExecutorInit.this.schedulerConfig.getExecutorCode());
                    if (Objects.equals(result, "SUCCESS")) {
                        return;
                    }

                    // 执行失败，60s后重试
                    ExecutorInit.logger.warn("Executor failed to register automatically, try again in {} seconds", ExecutorInit.this.schedulerConfig.getRetry());

                    try {
                        TimeUnit.SECONDS.sleep((long)ExecutorInit.this.schedulerConfig.getRetry());
                    } catch (Exception var4) {
                        ExecutorInit.logger.error("Actuator registration encountered an error");
                        return;
                    }
                }
            }
        }
    }

    /**
     * 扫描{@link JobHandler}注解，执行 定时任务的业务逻辑
     */
    private void scanJobHandler() {
        // key:SCEC.BP_PACKAGE_FAILURE value注解对象
        Map<String, Object> map = SpringContextUtils.getBeansWithAnnotation(JobHandler.class);
        for (Object service : map.values()) {
            // service为JobHandler注解标注的bean
            if (service instanceof IJobHandler) {
                JobHandler jobHandler = service.getClass().getAnnotation(JobHandler.class);
                if (ObjectUtils.isEmpty(jobHandler)) {
                    logger.debug("could not get target bean , jobHandler : {}", service);
                } else {
                    // SCEC.BP_PACKAGE_FAILURE service:业务方法类
                    JobRegistry.addJobHandler(jobHandler.value(), service);
                }
            }
        }
    }
}
