package com.ling.other.modules.scheduler.service;

import com.ling.other.modules.scheduler.myenum.ReturnT;
import com.ling.other.modules.scheduler.utils.SchedulerTool;

import java.util.Map;

/**
 * 任务执行器
 * @author zhangling
 * @since 2020/9/3 15:20
 */
public interface IJobHandler {

    /**
     * 任务执行
     *
     * @param map  任务参数
     * @param tool 工具
     * @return 任务结果
     */
    ReturnT execute(Map<String, String> map, SchedulerTool tool);
}
