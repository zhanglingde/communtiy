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
        SupplierUser user = new SupplierUser();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setCompanyId(1);
        //supplierUserService.createSupplierUser(user);


        List<Integer> list = new ArrayList<>();
        for (Integer integer : list) {
            System.out.println("测试");
        }
    }

    @Test
    public void createUser(){
        logger.info("测试开始");
        userService.createUser(User.builder()
                .username("zhansan")
                .gender("m")
                .age(18)
                .build());
        logger.debug("测试结束");
    }
}
