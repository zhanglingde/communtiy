package com.ling.other.controller;


import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.CommonResult;
import com.ling.other.entity.SupplierUser;
import com.ling.other.service.SupplierUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商用户表(AuSupplierUser)表控制层
 *
 * @author makejava
 * @since 2020-08-10 14:01:42
 */
@RestController
@RequestMapping("/user")
@Api(tags = "[api] 用户管理")
public class SupplierUserController {

    @Autowired
    private SupplierUserService supplierUserService;

    @ApiOperation(value = "供应商注册", notes = "供应商注册")
    @PostMapping("/create")
    public SupplierUser create(@RequestBody SupplierUser supplierUser) {

        supplierUserService.create(supplierUser);
        return supplierUser;

    }

    @GetMapping("/hello")
    public CommonResult hello(){

        supplierUserService.test();

        return CommonResult.success();
    }

    @GetMapping("/world")
    public CommonResult world() throws RrException  {
        int i = 1;
        if(i == 1){
            throw new RrException("自定义");
        }
        return CommonResult.success();

    }
}