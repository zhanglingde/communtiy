package com.ling.other.modules.scheduler.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRegistry {

    private ThreadRegistry() {
    }

    /**
     * <Thread, jobId>
     */
    private static Map<Thread, Long> jobThreadMap = new ConcurrentHashMap<>();

    /**
     * <threadId, iJobHandler>
     */
    private static Map<Long, Object> jobHandlerMap = new ConcurrentHashMap<>();


    public static List<Thread> getThread(Long jobId) {
        List<Thread> list = new ArrayList<>();
        jobThreadMap.forEach((k, v) -> {
            if (Objects.equals(v, jobId)) {
                list.add(k);
            }
        });
        return list;
    }

    public static void addThread(Thread thread, Long jobId) {
        jobThreadMap.put(thread, jobId);
    }

    public static void deleteThread(Thread thread) {
        jobThreadMap.remove(thread);
    }

    public static Object getJobHandler(Long threadId) {
        return jobHandlerMap.get(threadId);
    }

    public static void addJobHandler(Long threadId, Object jobHandler) {
        jobHandlerMap.put(threadId, jobHandler);
    }

    public static void deleteJobHandler(Long threadId) {
        jobHandlerMap.remove(threadId);
    }
}