package com.ling.other.common.constants;

/**
 * 接口常量
 * @author zhangling 2020/9/2 10:07
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

    interface Flag {
        Integer YES = 1;
        Integer NO = 0;
    }
}
