package com.ling.community.controller;

import com.ling.community.dto.AccessTokenDTO;
import com.ling.community.dto.GithubUser;
import com.ling.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,  //接收地址栏的参数
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user != null){
            // 登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/"; //重定向
        }else{
            // 登录失败，重新登录
            return "redirect:/"; //重定向
        }
    }
}
