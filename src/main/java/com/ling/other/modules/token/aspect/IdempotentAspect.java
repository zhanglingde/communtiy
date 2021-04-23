package com.ling.other.modules.token.aspect;

import com.ling.other.modules.token.TokenService;
import com.ling.other.modules.token.annotation.AutoIdempotent;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP处理接口幂等
 * @author zhangling 2021/4/8 16:39
 */
@Component
@Aspect
public class IdempotentAspect {

    @Autowired
    TokenService tokenService;

    @Pointcut("@annotation(com.ling.other.modules.token.annotation.AutoIdempotent)")
    public void pc1(){

    }

    @Before("pc1()")
    public void before() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            tokenService.checkToken(request);
        } catch (Exception e) {
            throw e;
        }

    }
}
