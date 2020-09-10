package com.ling.other.modules.scheduler.service;

import com.ling.other.entity.ExecutorDO;

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
}
