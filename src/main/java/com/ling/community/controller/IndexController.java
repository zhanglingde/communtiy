package com.ling.community.controller;

import com.ling.community.mapper.UserMapper;
import com.ling.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 把这个类作为路由API
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null){
                    // 根据自己设置的cookie设置session，如果数据库中有数据，则设置session，前端显示已登录
                    request.getSession().setAttribute("user",user);
                }
                break;
            }

        }

        return "index";
    }
}
