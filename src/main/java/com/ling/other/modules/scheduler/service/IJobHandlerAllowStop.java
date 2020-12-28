package com.ling.other.modules.scheduler.service;

public interface IJobHandlerAllowStop extends IJobHandler {

    /**
     * 允许停止
     *
     * @return boolean
     */
    default boolean allowStop() {
        return false;
    }

    /**
     * 任务暂停会执行的方法
     */
    default void onPause() {
    }

    /**
     * 任务停止补偿执行
     */
    default void onStop() {
    }
}