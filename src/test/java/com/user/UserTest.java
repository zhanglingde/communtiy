package com.user;

import com.ling.other.CommunityApplication;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.entity.SupplierUser;
import com.ling.other.modules.user.service.SupplierUserService;
import com.ling.other.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zhangling
 * @since 2020/12/9 10:45
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CommunityApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    SupplierUserService supplierUserService;

    @Autowired
    UserService userService;

    @Test
    public void createSupplierUser(){
        Queue<User> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.add(User.builder().id(i).username("用户"+i).build());
        }

        for (User user : queue) {
            System.out.println(user);
        }
    }

    @Test
    public void createUser(){
        logger.info("测试开始");
        //userService.createUser(User.builder()
        //        .username("zhansan")
        //        .gender("m")
        //        .age(18)
        //        .build());

        User user = new User().setUsername("zhangsan").setAge(18).setGender("m");
        System.out.println(user);
        logger.debug("测试结束");
    }
}
