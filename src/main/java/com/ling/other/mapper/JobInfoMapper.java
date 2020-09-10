package com.ling.other.mapper;

import com.ling.other.modules.scheduler.databoject.JobInfoDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zhangling
 * @since 2020/9/3 10:05
 */
@Repository
public interface JobInfoMapper extends Mapper<JobInfoDO> {
}
