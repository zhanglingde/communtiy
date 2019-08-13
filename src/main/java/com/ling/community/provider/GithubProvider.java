package com.ling.community.provider;

import com.alibaba.fastjson.JSON;
import com.ling.community.dto.AccessTokenDTO;
import com.ling.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 不需要实力化类了
 */
@Component  //把当前类初始化到Spring容器的上下文
public class GithubProvider {

    // 根据自己创建的 Oauth 地址获取access_token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        // 作post请求
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println("String:"+string);
//            String[] split = string.split("&"); //根据 & 拆分字符串
//            String tokenstr = split[0];
//            String token = tokenstr.split("=")[1]; // 根据 = 号拆分字符串
//            System.out.println(token);
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token:"+token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据token获取用户对象
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将string类型的json对象转换成java类型的类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return  githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
