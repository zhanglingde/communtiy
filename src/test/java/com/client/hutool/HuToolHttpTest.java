package com.client.hutool;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

/**
 * hutool 的Http访问测试
 * @author zhangling 2021/1/12 11:35
 */
public class HuToolHttpTest {

    @Test
    public void getTest(){
        String currency = "CNY";
        String url = "https://v6.exchangerate-api.com/v6/d87c79086f9a5dec75a4dd10/latest/" + currency;

        HttpUtil.get(url);
    }
}
