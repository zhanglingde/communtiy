package com.ling.other.mapper;

import com.ling.other.common.schedule.config.SysJobPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务测试持久层
 * @author zhangling
 * @since 2020/9/1 16:21
 */

@Repository
public interface DemoTestDao {

    boolean addSysJob(SysJobPO sysJob);


    List<SysJobPO> getSysJobListByStatus(int status);
}
