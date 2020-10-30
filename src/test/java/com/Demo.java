package com;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ling.other.common.utils.JsonUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author zhangling
 * @since 2020/10/28 13:39
 */
public class Demo {

    String url = "https://v6.exchangerate-api.com/v6/d87c79086f9a5dec75a4dd10/latest/USD";

    @Test
    public void demo01(){
        BigDecimal a = new BigDecimal(88);
        BigDecimal b = new BigDecimal(12);


        BigDecimal c = a.negate().add(b.negate());
        BigDecimal sum = a.negate().multiply(BigDecimal.valueOf(2));
        System.out.println(sum);
        System.out.println("c:"+c);
    }

    @Test
    public void httpTest(){
        HttpResponse execute = HttpRequest.get(url)
                .header(Header.CONTENT_TYPE,"application/json;charset=UTF-8")
                .timeout(20000)
                .execute();
        execute.getStatus();

        String body = execute.body();

        TaxRateVO temp = new TaxRateVO();
        temp.setBaseCode("USD");

        TaxRateVO taxRateVO = JsonUtils.fromJson(body, TaxRateVO.class);


        Gson gson = new Gson();
        String json1 = gson.toJson(taxRateVO);

        TaxRateVO taxRateVO1 = gson.fromJson(json1, TaxRateVO.class);

        //Object o = JSONObject.toJSON(taxRateVO);


        String json = JsonUtils.toJson(taxRateVO);
        System.out.println("status:"+execute.getStatus());
        System.out.println(execute.body());
    }
}
