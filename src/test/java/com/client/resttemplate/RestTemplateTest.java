//package com.client.resttemplate;
//
//import com.client.TaxRateVO;
//import com.ling.other.common.utils.CommonResult;
//import com.ling.other.common.utils.JsonUtils;
//import com.ling.other.modules.lov.vo.LovVO;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * RestTemplate 测试
// * @author zhangling 2021/1/29 15:17
// */
//public class RestTemplateTest {
//
//
//    RestTemplate restTemplate = new RestTemplate();
//
//    /**
//     * get请求
//     */
//    @Test
//    public void getTest(){
//
//        String url = "http://localhost:8088/api/lov/list";
//
//        // 返回json，自己反序列化
//        String json = restTemplate.getForObject(url, String.class);
//        // 直接转成对象
//        List<LovVO> list = restTemplate.getForObject(url, List.class);
//
//
//
//        restTemplate.getForObject(url+"/{1}",String.class,1);
//
//
//        // 带参请求   http://localhost:8088/api/lov/list?lovName=消息通知
//        Map<String,String> params = new HashMap<>();
//        params.put("lovName","消息通知状态");
//        params.put("lovCode","MESSAGE_STATUS");
//        List<LovVO> list2 = restTemplate.getForObject(url, List.class, params);
//
//
//        ResponseEntity<LovVO> forEntity = restTemplate.getForEntity(url, LovVO.class);
//        LovVO body = forEntity.getBody();
//
//
//    }
//}
