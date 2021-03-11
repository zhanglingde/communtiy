package com.ling.other.modules.user.service.impl;

import com.ling.other.common.exception.RrException;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/12/9 14:00
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    SupplierUserMapper supplierUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) {
        TransactionSynchronizationManager.getCurrentTransactionIsolationLevel();
        TransactionSynchronizationManager.isActualTransactionActive();
        TransactionSynchronizationManager.getCurrentTransactionName();
        for (int i = 1; i < 10; i++) {
            create(user);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User user) {
        for (int i = 1; i < 10; i++) {
            user.setUsername(String.valueOf(i));
            if (i == 5) {
                throw new RrException("异常");
            }
        }

        supplierUserMapper.insertUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreate(List<User> list) {
        Thread[] threads = new Thread[5];
        CountDownLatch latch = new CountDownLatch(threads.length);
        int j = list.size() / 5;
        List<User> list1 = list.subList(0, j);
        List<User> list2 = list.subList(j, 2 * j);
        List<User> list3 = list.subList(2 * j, 3 * j);
        List<User> list4 = list.subList(3 * j, 4 * j);
        List<User> list5 = list.subList(4 * j, list.size());

        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        try {
            threads[0] = new Thread(() -> {
                System.out.println("线程0："+TransactionSynchronizationManager.getCurrentTransactionName());
                for (User user : list1) {
                    try {
                        supplierUserMapper.insertUser(user);
                        if (user.getUsername().equals("线程池80")) {
                            throw new RrException("插入错误：" + user);
                        }
                    } catch (RrException e) {
                        logger.warn(Thread.currentThread().getName() + "运行错误：" + e.getMsg());
                        throw new RrException(e.getMsg());
                    }
                }

                latch.countDown();
            }, "线程0");
            threads[1] = new Thread(() -> {
                System.out.println("线程1："+TransactionSynchronizationManager.getCurrentTransactionName());
                for (User user : list2) {
                    try {
                        supplierUserMapper.insertUser(user);
                        if (user.getUsername().equals("线程池80")) {
                            throw new RrException("插入错误:" + user);
                        }
                    } catch (RrException e) {
                        logger.warn(Thread.currentThread().getName() + "运行错误：" + e.getMsg());
                        throw new RrException(e.getMsg());
                    }
                }
                latch.countDown();
            }, "线程1");
            threads[2] = new Thread(() -> {
                System.out.println("线程2："+TransactionSynchronizationManager.getCurrentTransactionName());
                for (User user : list3) {
                    try {
                        supplierUserMapper.insertUser(user);
                        if (user.getUsername().equals("线程池80")) {
                            throw new RrException("插入错误：" + user);
                        }
                    } catch (RrException e) {
                        logger.warn(Thread.currentThread().getName() + "运行错误：" + e.getMsg());
                        throw new RrException(e.getMsg());
                    }
                }
                latch.countDown();
            }, "线程2");
            threads[3] = new Thread(() -> {
                System.out.println("线程3："+TransactionSynchronizationManager.getCurrentTransactionName());
                for (User user : list4) {
                    try {
                        supplierUserMapper.insertUser(user);
                        if (user.getUsername().equals("线程池80")) {
                            throw new RrException("插入错误:" + user);
                        }
                    } catch (RrException e) {
                        logger.warn(Thread.currentThread().getName() + "运行错误：" + e.getMsg());
                        throw new RrException(e.getMsg());
                    }
                }
                latch.countDown();
            }, "线程3");
            threads[4] = new Thread(() -> {
                System.out.println("线程4："+TransactionSynchronizationManager.getCurrentTransactionName());
                for (User user : list5) {
                    try {
                        supplierUserMapper.insertUser(user);
                        if (user.getUsername().equals("线程池80")) {
                            throw new RrException("插入错误:" + user);
                        }
                    } catch (RrException e) {
                        logger.warn(Thread.currentThread().getName() + "运行错误：" + e.getMsg());
                        throw new RrException(e.getMsg());
                    }
                }
                latch.countDown();
            }, "线程4");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RrException("异常");
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("运行结束");

    }


}
