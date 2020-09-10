package com.ling.other.modules.scheduler.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * job存储类
 *
 * @author zhanglign
 */
public class JobRegistry {

    private JobRegistry() {
    }

    private static Map<String, Object> jobMap = new ConcurrentHashMap<>();

    public static void addJobHandler(String jobCode, Object handler) {
        jobMap.put(jobCode, handler);
    }

    public static Object getJobHandler(String jobHandler) {
        return jobMap.get(jobHandler);
    }
}