package com.ling.other.modules.user.controller;


import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.CommonResult;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.lov.annotation.ProcessLovValue;
import com.ling.other.modules.lov.vo.LovVO;
import com.ling.other.modules.user.dto.SupplierUserDTO;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.entity.SupplierUser;
import com.ling.other.modules.user.service.SupplierUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    SupplierUserMapper userMapper;

    @ApiOperation(value = "供应商注册", notes = "供应商注册")
    @PostMapping("/create")
    public SupplierUser create(@RequestBody SupplierUser supplierUser) {
        supplierUserService.createSupplierUser(supplierUser);
        return supplierUser;

    }

    @ApiOperation(value = "User新增", notes = "User新增")
    @PostMapping("/createuser")
    public User createUser(@RequestBody User user) {
        supplierUserService.createUser(user);
        return user;
    }



    @ApiOperation(value = "供应商列表", notes = "供应商列表")
    @GetMapping("/list")
    @ProcessLovValue(targetField = "data")
    public CommonResult<List<SupplierUser>> list(){
        return CommonResult.success();
    }

    @GetMapping("/hello")
    public CommonResult hello(String num){
        System.out.println("hello"+num);
        return CommonResult.success();
    }



    @GetMapping("/world")
    public LovVO world(){
        int i = 1;
        LovVO lovVO = new LovVO();
        lovVO.setLovCode("RestTemplate");
        lovVO.setDescription("请求测试");
        lovVO.setLovId(1);
        lovVO.setLovName("RestTemplate请求");

        return lovVO;
    }

    @GetMapping("/order")
    public void pur(){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(User.builder().username("测试"+i).build());
        }

        supplierUserService.test(list);

    }

    @GetMapping("/order/update")
    public void update(Integer id){
        userMapper.updateById(id);
    }
}