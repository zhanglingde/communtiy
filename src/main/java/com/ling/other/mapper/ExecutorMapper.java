package com.ling.other.mapper;

import com.ling.other.entity.ExecutorDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * @author zhangling
 * @since 2020/9/3 10:36
 */
@Repository
public interface ExecutorMapper extends Mapper<ExecutorDO> {
}
