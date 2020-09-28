package com.ling.other.modules.coderule.service.impl;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.ling.other.common.exception.RrException;
import com.ling.other.mapper.CodeRuleDistMapper;
import com.ling.other.mapper.CodeRuleMapper;
import com.ling.other.modules.coderule.dto.CodeRuleDTO;
import com.ling.other.modules.coderule.dto.CodeRuleDistDTO;
import com.ling.other.modules.coderule.service.CodeRuleDistService;
import com.ling.other.modules.coderule.service.CodeRuleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangling
 * @since 2020/9/28 14:35
 */
@Service
public class CodeRuleServiceImpl implements CodeRuleService {

    @Autowired
    CodeRuleDistMapper codeRuleDistMapper;

    @Autowired
    CodeRuleMapper codeRuleMapper;

    @Autowired
    CodeRuleDistService codeRuleDistService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CodeRuleDTO insertOrUpdate(CodeRuleDTO codeRule) {

        if (codeRule.getRuleId() != null) {
            // 新增时校验该编码是否存在重复
            CodeRuleDTO tempCodeRule = new CodeRuleDTO();
            tempCodeRule.setRuleCode(codeRule.getRuleCode());
            if (CollectionUtils.isNotEmpty(this.codeRuleMapper.select(tempCodeRule))) {
                throw new RrException("error.data_exists");
            }

            this.codeRuleMapper.insertSelective(codeRule);
            // 插入规则明细行信息
            CodeRuleDistDTO codeRuleDistDTO = new CodeRuleDistDTO();
            codeRuleDistDTO.setRuleId(codeRule.getRuleId());
            codeRuleDistDTO.setEnabledFlag(0);
            codeRuleDistDTO.setLevelCode("GLOBAL");
            codeRuleDistDTO.setLevelValue("GLOBAL");
            codeRuleDistMapper.insertSelective(codeRuleDistDTO);
            //this.codeRuleDistMapper.insertGlobalDist(codeRule.getRuleId());
            //this.codeRuleRepository.clearFailFastCache(codeRule.getTenantId(), codeRule.getRuleCode(), codeRule.getRuleLevel(), "GLOBAL", "GLOBAL");
        } else {
            this.codeRuleMapper.updateByPrimaryKey(codeRule);
        }

        if (CollectionUtils.isNotEmpty(codeRule.getCodeRuleDistDTOList())) {
            codeRule.getCodeRuleDistDTOList().forEach((item) -> {
                item.setRuleId(codeRule.getRuleId());
                this.codeRuleDistService.insertOrUpdate(item);
            });
        }
        return codeRule;
    }
}
