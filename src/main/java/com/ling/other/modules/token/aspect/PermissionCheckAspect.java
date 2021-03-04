package com.ling.other.modules.token.aspect;


import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.protocol.ResponseCode;
import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.token.annotation.Permission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 权限校验AOP
 *
 * @author zhangling
 * @since 2020/7/20 15:09
 */
@Aspect
@Component("permission")
public class PermissionCheckAspect {

    @Autowired
    // private TokenUtil tokenUtil;

    /**
     * 切点表达式
     */
    @Pointcut(value = "@annotation(com.ling.other.modules.token.annotation.Permission)")
    private void permissionCheckCut(){

    }

    /**
     * 校验登录
     * @param pjp
     * @return
     */
    @Around("permissionCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object rtValue = null;
        try {
            // 权限校验
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method targetMethod = methodSignature.getMethod();

            if(targetMethod.isAnnotationPresent(Permission.class)){
                Permission permission = targetMethod.getAnnotation(Permission.class);

                // 使用了注解
                if(Permission.PermissionStatus.LOGIN.equals(permission.value())){
                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    // 获得token
                    String token = request.getHeader("token");

                    // 校验token是否有效
                    // if(tokenUtil.isTokenExpired(token)){
                    //     return CommonResult.error(ResponseCode.TOKEN_EXPIRED.getCode(),ResponseCode.TOKEN_EXPIRED.getMessage());
                    // }
                    // Integer userId = tokenUtil.getUserId(token);
                    // Integer companyId = tokenUtil.getUserGroupId(token);
                    // if (null == userId || companyId == null) {
                    //     return CommonResult.error(ResponseCode.NO_LOGIN.getCode(),ResponseCode.NO_LOGIN.getMessage());
                    // }

                    // 将token值设置当前线程全局有效
                    // SupplierToken supplierToken = new SupplierToken();
                    // supplierToken.setCompanyId(companyId);
                    // supplierToken.setUserId(userId);
                    // supplierToken.setToken(token);
                    // TokenThreadLocal.setTokenLocal(supplierToken);
                }
            }
            Object[] args = pjp.getArgs();
            rtValue = pjp.proceed(args);
            // TokenThreadLocal.removeToken();
            return rtValue;
        } catch (Throwable t) {
            throw t;
        }
    }

    enum ResponseCode {

        /**
         * token失效
         */
        TOKEN_EXPIRED(401,"token失效"),

        /**
         * 未登录，app全局判断
         */
        NO_LOGIN(200,"请登录"),

        /**
         * 出错
         */
        ERROR(500,"出错");

        /**
         * code
         */
        private Integer code;
        /**
         * 提示内容
         */
        private String message;

        ResponseCode( Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public Integer getCode() {
            return code;
        }
    }
}
