package com.ling.other.modules.batchthread;

import com.ling.other.CommunityApplication;
import com.ling.other.common.exception.RrException;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.service.UserService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling 2021/3/11 11:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test01 {

    @Autowired
    SupplierUserMapper supplierUserMapper;

    @Autowired
    UserService userService;

    @Test
    public void test(){
        // 监控耗时
        StopWatch sw = new StopWatch();
        sw.start();
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(User.builder().username("测试"+i).build());
        }
        OrderExecutor<User> executor = new OrderExecutor<>(2, list);
        executor.setCallBack(new OrderExecutor.CallBack<User>() {
            @Override
            public void method(List<User> list) {
                // 业务方法
                for (User user : list) {
                    supplierUserMapper.insertUser(user);
                    if (user.getUsername().equals("测试8")) {
                        throw new RrException("测试异常");
                    }
                }
            }
        });
        try {
            executor.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常");
        }
        System.out.println("总耗时："+sw.getTotalTimeMillis());
    }

    @Test
    public void test3() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(User.builder().username("ddd"+i).build());
        }
        userService.batchCreate(list);
    }
}
