package com.client.okhttp;

import com.client.TaxRateVO;
import com.ling.other.common.utils.JsonUtils;
import com.ling.other.modules.lov.dto.LovValueDTO;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

/**
 * OkHttp测试 zhangling 2021/1/12 11:25
 */
public class OkHttpTest {

    /**
     * get请求
     */
    @Test
    public void getTest(){
        String currency = "CNY";
        String url = "https://v6.exchangerate-api.com/v6/d87c79086f9a5dec75a4dd10/latest/" + currency;

        OkHttpClient client = new OkHttpClient();
        // 构建请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            // 执行请求
            Response response = client.newCall(request).execute();
            String body= response.body().string();
            TaxRateVO taxRateVO = JsonUtils.fromJson(body, TaxRateVO.class);
            System.out.println(taxRateVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求
     */
    @Test
    public void postTest(){
        MediaType JSON = MediaType.get("application/json;charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url = "http://localhost:8088/api/lov/value/create";

        // post请求请求体
        LovValueDTO build = LovValueDTO.builder().lovId(1).lovCode("Client_Test").value("okhttp").meaning("okhttp测试").build();
        String json = JsonUtils.toJson(build);
        RequestBody body = RequestBody.create(JSON, json);

        // 构建请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()){
            // 执行后获得返回值
            String bodyString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 有返回值 post请求
     */
    @Test
    public void postResponseTest() {
        MediaType JSON = MediaType.get("application/json;charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url = "http://localhost:8088/api/lov/value/update/value";

        // post请求请求体
        LovValueDTO build = LovValueDTO.builder().lovValueId(161).lovId(1).lovCode("Client_Test").value("okhttp").meaning("okhttp测试").orderSeq(20).build();
        String json = JsonUtils.toJson(build);
        RequestBody body = RequestBody.create(JSON, json);

        // 构建请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        //try (Response response = client.newCall(request).execute()){
        //    // 执行后获得返回值
        //    String bodyString = response.body().string();
        //    LovValueDTO lovValueDTO = JsonUtils.fromJson(bodyString, LovValueDTO.class);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    public void a(){

    }
}
