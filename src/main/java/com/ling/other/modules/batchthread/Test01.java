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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    //@Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void test(){
        // 监控耗时
        StopWatch sw = new StopWatch();
        sw.start();
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(User.builder().username("1010"+i).id(i).build());
        }
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        OrderExecutor<User> executor = new OrderExecutor<>(2, list);
        executor.setCallBack(new OrderExecutor.CallBack<User>() {
            @Override
            public void method(List<User> list, Queue queue) {
                // 业务方法
                for (User user : list) {
                    supplierUserMapper.insertUser(user);
                    queue.add(user.getId());
                    if (user.getUsername().equals("测试8")) {
                        throw new RrException("测试异常");
                    }
                }
            }
        });
        //Queue<TransactionStatus> execute;
        try {
            executor.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RrException(e.getMessage());
        }
        //if (execute == null) {
        //    return;
        //}

        // 远程 业务代码
        //try {
        //    System.out.println("总耗时："+sw.getTotalTimeMillis());
        //
        //    for (TransactionStatus transactionStatus : execute) {
        //        transactionManager.commit(transactionStatus);
        //    }
        //} catch (Exception e) {
        //    for (TransactionStatus transactionStatus : execute) {
        //        transactionManager.rollback(transactionStatus);
        //    }
        //    throw new RrException("远程代码异常");
        //}
    }


}
