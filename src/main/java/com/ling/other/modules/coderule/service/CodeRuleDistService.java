package com.ling.other.modules.coderule.service;

import com.ling.other.modules.coderule.dto.CodeRuleDistDTO;

/**
 * @author zhangling
 * @since 2020/9/28 15:04
 */
public interface CodeRuleDistService {

    /**
     * 插入编码规则分配 行信息
     * @param item
     */
    void insertOrUpdate(CodeRuleDistDTO item);
}
