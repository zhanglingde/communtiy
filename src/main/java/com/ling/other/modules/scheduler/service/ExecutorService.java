package com.ling.other.modules.scheduler.service;

import com.ling.other.modules.scheduler.databoject.ExecutorDO;

/**
 * @author zhangling
 * @since 2020/9/3 13:55
 */
public interface ExecutorService {

    /**
     * 创建执行器
     * @param executor
     * @return
     */
    ExecutorDO createExecutor(ExecutorDO executor);

    /**
     * 客户端刷新执行器
     * @param executorCode
     * @return
     */
    String refreshExecutor(String executorCode);
}
