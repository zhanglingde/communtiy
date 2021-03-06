package com.ling.other.mapper;

import com.ling.other.modules.coderule.dto.CodeRuleDTO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 编码规则持久层
 *
 * @author zhangling
 * @since 2020/9/28 14:39
 */
@Repository
public interface CodeRuleMapper extends Mapper<CodeRuleDTO> {
}
