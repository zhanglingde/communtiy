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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 * @author zhangling
 * @since 2020/8/11 15:20
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";


    /**
     * 未定义异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult errorMsg(HttpServletRequest request, Exception ex) {
        Integer userId = 0;
        String floatform ="";
        String version ="";
        if(null != request){
            floatform = request.getHeader("platform");
            version = request.getHeader("version");
        }
        Map<String,Object> param = paramToString(request);
        String errorContent = ex2String(ex);
        logger.error("###未知异常拦截###,app类型："+floatform+",版本:"+version+",用户id:"+userId+",错误地址：" + request.getRequestURL().toString() +",传递参数:"+param.toString()+"错误类型："+ex.toString()+",错误内容："+ errorContent + ",#####异常结束");
        return CommonResult.error("未知异常");
    }

    /**
     * 异常信息转字符串
     * @param t
     * @return
     */
    private String ex2String(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    /**
     * 获取传递的参数。
     * @param request
     * @return
     */
    private  Map<String,Object> paramToString (HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length >0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
//        Set<Map.Entry<String, Object>> set = map.entrySet();
//        for (Map.Entry entry : set) {
//           System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
        return map;
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
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            // 首先根据请求Url查找有没有对应的控制器，若没有则会抛该异常，也就是大家非常熟悉的404异常；
            NoHandlerFoundException.class,
            /**
             * 若匹配到了（匹配结果是一个列表，不同的是http方法不同，如：Get、Post等），则尝试将请求的http方法与列表的控制器做匹配，
             * 若没有对应http方法的控制器，则抛该异常；
             */
            HttpRequestMethodNotSupportedException.class,
            /**
             * 然后再对请求头与控制器支持的做比较，比如content-type请求头，若控制器的参数签名包含注解@RequestBody，
             * 但是请求的content-type请求头的值没有包含application/json，那么会抛该异常（当然，不止这种情况会抛这个异常）；
             */
            HttpMediaTypeNotSupportedException.class,
            /**
             * 未检测到路径参数。比如url为：/licence/{licenceId}，参数签名包含@PathVariable("licenceId")，当请求的url为/licence，
             * 在没有明确定义url为/licence的情况下，会被判定为：缺少路径参数；
             */
            MissingPathVariableException.class,
            /**
             * 缺少请求参数。比如定义了参数@RequestParam("licenceId") String licenceId，但发起请求时，未携带该参数，则会抛该异常；
             */
            MissingServletRequestParameterException.class,
            /**
             * 参数类型匹配失败。比如：接收参数为Long型，但传入的值确是一个字符串，那么将会出现类型转换失败的情况，这时会抛该异常；
             */
            TypeMismatchException.class,
            /**
             * 与上面的HttpMediaTypeNotSupportedException举的例子完全相反，即请求头携带了"content-type: application/json;charset=UTF-8"，
             * 但接收参数却没有添加注解@RequestBody，或者请求体携带的 json 串反序列化成 pojo 的过程中失败了，也会抛该异常；
             *
             */
            HttpMessageNotReadableException.class,
            // 返回的 pojo 在序列化成 json 过程失败了，那么抛该异常；
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
        int code = 500;
        try {
            //ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
            //code = servletExceptionEnum.getCode();
            code = 500;
        } catch (IllegalArgumentException e1) {
            logger.error("class [{}] not defined in enum {}", e.getClass().getName());
        }

        if (ENV_PROD.equals("profile")) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
            code = 500;
            RrException rrException = new RrException("网络异常");
            //BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
            //String message = getMessage(rrException);
            String message = "网络异常";
            return CommonResult.error(code, message);

        }

        return CommonResult.error(code, e.getMessage());
    }



}
