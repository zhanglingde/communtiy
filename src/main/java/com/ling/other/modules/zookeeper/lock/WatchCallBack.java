package com.ling.other.modules.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/10/27 16:09
 */
public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback {

    ZooKeeper zk;
    String threadName;
    CountDownLatch cc = new CountDownLatch(1);
    String pathName;

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public CountDownLatch getCc() {
        return cc;
    }

    public void setCc(CountDownLatch cc) {
        this.cc = cc;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void tryLock() {
        try {
            System.out.println(threadName);
            // 获取锁：创建一个序列节点
            // 回调为String回调
            zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "abc");
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void unLock() {

    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (name != null) {
            System.out.println(threadName + "create node：" + name);
            pathName = name;
            //    序列节点创建成功
            // 判断是否是最小的那个节点，只有最小的那个才可以获得锁，不需要监控watch
            zk.getChildren("/", false, this, "sdf");
        }
    }

    // getChildren : callback
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        //自己创建完了，能看到自己和自己前面创建的节点

        System.out.println(threadName + "look locks...");
        for (String child : children) {
            System.out.println(child);
        }
    }
}
