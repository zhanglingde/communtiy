package com.ling.community.controller;

import com.ling.community.dto.AccessTokenDTO;
import com.ling.community.dto.GithubUser;
import com.ling.community.mapper.UserMapper;
import com.ling.community.model.User;
import com.ling.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired  //自动把Spring实例化好的实例自动加载到当前上下文
    private GithubProvider githubProvider;
    @Value("${github.client.id}")  // 从配置文件读取
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,  //接收地址栏的参数
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        // 登录成功后不写session、而是插入写入数据库
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);// 插入数据库，用数据库代替session
            // 登录的时候自己设置一个cookie，用这个cookie去和服务器交互，根据这个cookie去数据库查询，进行判断是否登录
            response.addCookie(new Cookie("token",token));
            // 登录成功，写cookie和session
            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/"; //重定向
        }else{
            // 登录失败，重新登录
            return "redirect:/"; //重定向
        }
    }
}
