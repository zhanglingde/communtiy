package com.ling.other.mapper;

import com.ling.other.entity.Task;
import com.ling.other.modules.schedule_java.config.SysJobPO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 定时任务测试持久层
 * @author zhangling
 * @since 2020/9/1 16:21
 */
@Repository
public interface TaskMapper extends Mapper<Task> {

    boolean addSysJob(SysJobPO sysJob);


    /**
     * 查询定时任务列表
     * @param status
     * @return
     */
    List<SysJobPO> getSysJobListByStatus(int status);

    /**
     * 编辑定时任务
     * @param task
     * @return
     */
    boolean updateTask(Task task);
}
