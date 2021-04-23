package com.ling.other.modules.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/10/27 16:09
 */
public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

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


    //======================getter/setter============================

    public void tryLock() {
        try {
            System.out.println(threadName);
            //在第一个节点获取到锁的时候设置数据，后续获取这个数据，对其进行判断，可以设置重入锁
            // if(zk.getData("/")

            // 获取锁：创建一个序列节点
            // 回调为String回调
            zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "abc");
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void unLock() {
        try {
            zk.delete(pathName,-1);
            System.out.println("end--------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        // 如果是第一个挂了，只有第二个收到了回调事件
        // 如果，不是第一个挂了，其中一个挂了，也能造成它这个后面收到这个通知，从而造成它后面的监控挂了的消息前面的消息
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            //   被删除时，下一个节点判断是否是第一个节点，获取锁
            case NodeDeleted:
                zk.getChildren("/",false,this,"sdf");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if (name != null) {
            System.out.println(threadName + "create node：" + name);
            pathName = name;
            //    序列节点创建成功
            // 判断是否是最小的那个节点，只有最小的那个才可以获得锁，不需要监控watch
            // 不需要watch，不需要关注锁目录的变化，只需要监控前一个序列节点的事件
            zk.getChildren("/", false, this, "sdf");
        }
    }

    // getChildren : callback
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        //自己创建完了，能看到自己和自己前面创建的节点

        // System.out.println(threadName + "look locks...");
        // for (String child : children) {
        //     System.out.println(child);
        // }

        Collections.sort(children);
        int i = children.indexOf(pathName.substring(1));

        // 是不是第一个
        if (i == 0) {
            //    是第一个
            System.out.println(threadName + "I am first...");
            try {

                zk.setData("/", threadName.getBytes(), -1);
                // 第一个释放开阻塞
                cc.countDown();

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            // 当前节点不是第一个的时候，判断前一个节点是否存在，并监控watch前一个节点
            zk.exists("/"+children.get(i-1),this,this,"sdf");
        }
    }

    // zk.exists : callback
    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

    }
}
