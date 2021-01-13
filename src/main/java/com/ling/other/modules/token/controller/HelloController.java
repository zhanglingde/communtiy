package com.ling.other.modules.token.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.token.TokenService;
import com.ling.other.modules.token.annotation.AutoIdempotent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义 注解测试，使用拦截器处理请求注解，权限拦截等等
 * @author zhangling 2020/9/25 14:54
 */
@RestController
public class HelloController {

    @Autowired
    TokenService tokenService;

    /**
     * 使用gettoken在redis中创建一个token
     * @return
     */
    @GetMapping("/gettoken")
    public CommonResult<String> getToken(){
        return CommonResult.success(tokenService.createToken());
    }

    /**
     * {@link AutoIdempotent}注解校验幂等性
     * 使用创建的token调用一次接口，一个token只能调用一次接口
     * @return
     */
    @GetMapping("/hello")
    @AutoIdempotent
    public CommonResult<String> hello(){
        return CommonResult.success("hello");
    }

    @GetMapping("/hello2")
    public CommonResult<String> hello2(){
        return CommonResult.success("hello2");
    }
}
