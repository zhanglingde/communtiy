package com.ling.other.modules.zookeeper.lock;

import com.ling.other.modules.zookeeper.config.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhangling
 * @since 2020/10/27 16:05
 */
public class TestLock {

    ZooKeeper zk;


    @Before
    public void conn() {
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock() {

        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    WatchCallBack watchCallBack = new WatchCallBack();
                    watchCallBack.setZk(zk);
                    String threadName = Thread.currentThread().getName();
                    watchCallBack.setThreadName(threadName);
                    // 每一个线程
                    // 抢锁
                    watchCallBack.tryLock();
                    //干活
                    System.out.println("do something...");
                    //释放锁
                    watchCallBack.unLock();


                }
            }.start();
        }

        while(true){

        }
    }
}
