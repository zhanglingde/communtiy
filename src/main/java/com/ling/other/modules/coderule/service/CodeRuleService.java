package com.ling.other.modules.coderule.service;

import com.ling.other.modules.coderule.dto.CodeRuleDTO;

/**
 * @author zhangling
 * @since 2020/9/28 14:35
 */
public interface CodeRuleService {

    /**
     * 新增和更新编码规则头
     *
     * @param codeRule
     * @return
     */
    CodeRuleDTO insertOrUpdate(CodeRuleDTO codeRule);
}
