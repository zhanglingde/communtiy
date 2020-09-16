package com.ling.other.modules.lov.aspect;


import com.ling.other.modules.lov.annotation.ProcessLovValue;
import com.ling.other.modules.lov.handle.LovValueHandle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 用以处理值集映射的切面
 * @author zhangling
 * @since 2020/7/24 16:14
 */
@Aspect
@Component
public class LovValueAspect {

    @Autowired
    private LovValueHandle lovValueHandle;

    public LovValueAspect(LovValueHandle lovValueHandle) {
        this.lovValueHandle = lovValueHandle;
    }

    @AfterReturning(value = "@annotation(com.ling.other.modules.lov.annotation.ProcessLovValue)",returning = "result")
    public Object afterReturning(JoinPoint proceedingJoinPoint, Object result) throws Throwable{
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        ProcessLovValue processLovValue = method.getAnnotation(ProcessLovValue.class);
        result = this.lovValueHandle.process(processLovValue.targetField(),result);
        return result;
    }
}
