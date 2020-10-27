package com.ling.other.modules.zookeeper.config;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/10/27 13:46
 */
public class ZKUtils {

    private static ZooKeeper zk;

    private static String address = "192.168.191.128:2181,192.168.191.129:2181,192.168.191.130:2181,192.168.191.132:2181/testConf";

    private static DefaultWatch watch = new DefaultWatch();

    private static CountDownLatch init = new CountDownLatch(1);

    public static ZooKeeper getZK(){

        try {
            zk = new ZooKeeper(address, 1000, watch);
            watch.setCd(init);
            // zk没有连接成功就阻塞住，直到Watch中连接成功后放开阻塞
            init.await();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return zk;
    }


}
