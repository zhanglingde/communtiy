package com.ling.other.modules.lov.controller;


import com.ling.other.common.utils.CommonResult;
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
    public CommonResult<List<LovVO>> list(LovSearchDTO lovSearchDTO){
        return CommonResult.success(lovApplication.list(lovSearchDTO));
    }

    @ApiOperation(value = "值集详情", notes = "值集详情")
    @GetMapping("/details")
    public CommonResult<LovVO> details(Integer lovId){
        return CommonResult.success(lovApplication.details(lovId));
    }

    @ApiOperation(value = "新增值集", notes = "新增值集")
    @PostMapping("/create")
    public CommonResult createLov(@RequestBody LovDTO lovDTO) {

        lovApplication.createLov(lovDTO);
        return CommonResult.success(null, "操作成功");
    }

    @ApiOperation(value = "编辑值集", notes = "编辑值集")
    @PostMapping("/update")
    public CommonResult updateLov(@RequestBody LovVO lovVO){

        lovApplication.updateLov(lovVO);
        return CommonResult.success(null,"操作成功");
    }

    @ApiOperation(value = "查询值集value", notes = "查询值集value")
    @GetMapping("/value/query")
    public CommonResult<List<LovValueVO>> queryLovValue(@ApiParam("值集编码") @RequestParam(required = true) String lovCode) {
        List<LovValueVO> list = lovApplication.queryLovValue(lovCode);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "创建值集value", notes = "创建值集value")
    @PostMapping("/value/create")
    public CommonResult createValue(@RequestBody List<LovValueDTO> lovValueDTOList) {

        lovApplication.createValue(lovValueDTOList);
        return CommonResult.success(null, "创建成功");
    }

    @ApiOperation(value = "修改值集value", notes = "修改值集value")
    @PostMapping("/value/update")
    public CommonResult updateValue(@RequestBody LovValueDTO lovValueDTO) {

        lovApplication.updateValue(lovValueDTO);
        return CommonResult.success(null, "修改成功");
    }

    @ApiOperation(value = "删除值集value", notes = "删除值集value")
    @PostMapping("/value/delete")
    public CommonResult deleteValue(@RequestParam Integer lovValueId) {

        lovApplication.deleteValue(lovValueId);
        return CommonResult.success(null, "删除成功");

    }
}