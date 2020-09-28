package com.ling.other.modules.coderule.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.coderule.dto.CodeRuleDTO;
import com.ling.other.modules.coderule.service.CodeRuleService;
import com.ling.other.modules.token.annotation.AutoIdempotent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangling
 * @since 2020/9/28 14:29
 */
@RestController
@Api(tags = "[api] 编码规则")
public class CodeRuleManagerController {

    @Autowired
    CodeRuleService codeRuleService;


    @ApiOperation(value = "新增和更新编码规则头",notes = "新增和更新编码规则头")
    @PostMapping({"/code-rule"})
    public CommonResult<CodeRuleDTO> insertOrUpdate(@RequestBody CodeRuleDTO  codeRule) {

        return CommonResult.success(this.codeRuleService.insertOrUpdate(codeRule));
    }

}
