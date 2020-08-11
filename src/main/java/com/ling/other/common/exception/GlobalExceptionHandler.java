package com.ling.other.common.exception;

import com.ling.other.common.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author zhangling
 * @since 2020/8/11 15:20
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    public CommonResult errorMsg(HttpServletRequest request, Exception ex) {
        Integer userId = 0;
        String floatform ="";
        String version ="";
        if(null != request){
            floatform = request.getHeader("platform");
            version = request.getHeader("version");
        }
        //Map<String,Object> param = paramToString(request);
        //String errorContent = ex2String(ex);
        logger.error("###未知异常拦截###,app类型："+floatform+",版本:"+version+",用户id:"+userId+",错误地址：" + request.getRequestURL().toString() +",传递参数:"+"param.toString()"+"错误类型："+ex.toString()+",错误内容："+ "errorContent" + ",#####异常结束");
        return CommonResult.error("未知异常");
    }

    /**
     * 自定义异常
     *
     * @param r 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = RrException.class)
    public CommonResult handleRrException(RrException r){
        System.out.println("自定义异常");
        logger.error(r.getMsg(),r);
        return CommonResult.error(500,r.getMsg());
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            // BindException.class,
            // MethodArgumentNotValidException.class
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public CommonResult handleServletException(Exception e) {
        logger.error(e.getMessage(), e);
        //int code = CommonResponseEnum.SERVER_ERROR.getCode();
        int code = 500;
        //try {
        //    ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
        //    code = servletExceptionEnum.getCode();
        //} catch (IllegalArgumentException e1) {
        //    logger.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        //}

        //if (ENV_PROD.equals(profile)) {
        //        //    // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
        //        //    code = CommonResponseEnum.SERVER_ERROR.getCode();
        //        //    BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
        //        //    String message = getMessage(baseException);
        //        //    return new ErrorResponse(code, message);
        //        //}

        return CommonResult.error(code,e.getMessage());
    }



}
