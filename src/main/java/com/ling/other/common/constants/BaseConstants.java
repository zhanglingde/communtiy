package com.ling.other.common.constants;

/**
 * 基本常量
 * @author zhangling
 * @since 2020/9/2 10:07
 */
public interface BaseConstants {

    /**
     * 定时任务状态
     */
    interface TaskStatus{
        /**
         * 正常
         */
        Integer NORMAL = 1;

        /**
         * 暂停
         */
        Integer PAUSE = 2;
    }
}
