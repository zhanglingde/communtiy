package com.ling.other.modules.token.interceptor;

import com.ling.other.common.exception.RrException;
import com.ling.other.modules.token.TokenService;
import com.ling.other.modules.token.annotation.AutoIdempotent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *
 * 自定义注解{@link AutoIdempotent}的拦截器
 * @author zhangling 2020/9/25 14:32
 */
@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    /**
     * 请求前的拦截处理
     * @param request
     * @param response
     * @param handler
     * @return 只有返回true，请求才可以继续执行，返回false请求不会继续执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // Controller中定义的方法
        Method method = ((HandlerMethod) handler).getMethod();
        AutoIdempotent annotation = method.getAnnotation(AutoIdempotent.class);  // 方法上有注解
        if (annotation != null) {
            try {
                // 有注解进行校验token
                return tokenService.checkToken(request);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RrException(e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
