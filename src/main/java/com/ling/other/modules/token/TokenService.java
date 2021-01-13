package com.ling.other.modules.token;

import com.ling.other.common.exception.RrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author zhangling 2020/9/25 14:12
 */
@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken(){
        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid,uuid,10000L);
        return uuid;
    }

    /**
     * 校验接口的传的token有没有效，
     * 一个token只能用一次，使用完后从redis中删除，
     * 保证一个接口在同一个业务逻辑中只能调用一次，保证接口的幂等性
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
            if(StringUtils.isEmpty(token)){
                throw new RrException("token 不存在");
            }
        }
        if(!redisService.exists(token)){
            throw new RrException("重复的操作");
        }
        // token存在从redis中移除token
        boolean remove = redisService.remove(token);
        if(!remove){
            throw new RrException("重复的操作");
        }
        return true;
    }
}
