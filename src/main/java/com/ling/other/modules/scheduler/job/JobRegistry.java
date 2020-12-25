package com.ling.other.modules.scheduler.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * job存储类：存储所有的定时任务JobCode和业务代码的Bean
 *
 * @author zhanglign
 */
public class JobRegistry {

    private JobRegistry() {
    }

    private static Map<String, Object> jobMap = new ConcurrentHashMap<>();

    /**
     * 将
     * @param jobCode JobHandler注解上标注的定时任务的jobCode
     * @param handler 自定义的需要定时任务执行的bean，实现IJobHandler接口的业务类
     */
    public static void addJobHandler(String jobCode, Object handler) {
        jobMap.put(jobCode, handler);
    }

    public static Object getJobHandler(String jobHandler) {
        return jobMap.get(jobHandler);
    }
}