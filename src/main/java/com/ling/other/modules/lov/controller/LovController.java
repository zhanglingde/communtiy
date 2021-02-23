package com.ling.other.modules.lov.controller;


import com.ling.other.common.constants.CommonParams;
import com.ling.other.common.utils.CommonResult;
import com.ling.other.common.utils.PageUtils;
import com.ling.other.modules.lov.application.LovApplication;
import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovSearchDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovVO;
import com.ling.other.modules.lov.vo.LovValueVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * lov表(Lov)表控制层
 *
 * @author zhangling
 * @since 2020-07-27 15:51:48
 */
@RestController
@RequestMapping("/api/lov")
@Api(tags = "[API] LOV值集管理")
public class LovController {

    @Autowired
    private LovApplication lovApplication;

    @ApiOperation(value = "值集列表", notes = "值集列表")
    @GetMapping("/list")
    public List<LovVO> list(LovSearchDTO lovSearchDTO){
        return lovApplication.list(lovSearchDTO);
    }

    @ApiOperation(value = "值集详情", notes = "值集详情")
    @GetMapping("/details")
    public CommonResult<LovVO> details(Integer lovId){
        return CommonResult.success(lovApplication.details(lovId));
    }

    @ApiOperation(value = "新增值集header", notes = "新增值集header")
    @PostMapping("/create")
    public CommonResult createLov(@RequestBody LovDTO lovDTO) {
        lovApplication.createLov(lovDTO);
        return CommonResult.success(null, "操作成功");
    }

    @ApiOperation(value = "编辑值集header", notes = "编辑值集header")
    @PostMapping("/update")
    public CommonResult updateLov(@RequestBody LovDTO lovDTO){
        lovApplication.updateLov(lovDTO);
        return CommonResult.success("操作成功");
    }
}