package com.ling.other.modules.zookeeper.config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/10/27 14:15
 */
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    ZooKeeper zk;
    MyConf conf;

    CountDownLatch cc = new CountDownLatch(1);

    public MyConf getConf() {
        return conf;
    }

    public void setConf(MyConf conf) {
        this.conf = conf;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void aWait(){
        // 异步
        zk.exists("/AppConf",this,this ,"ABC");

        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if(data != null){
            String s = new String(data);
            // 更新配置
            conf.setConf(s);
            cc.countDown();

        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            //  AppConf节点存在，回调调用processResult(int rc, String path, Object ctx, byte[] data, Stat stat)
            zk.getData("/AppConf",this,this,"ctx");

        }
    }

    /**
     * 事件回调方法
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                // 节点被创建
                zk.getData("/AppConf",this,this,"ctx");
                break;
            case NodeDeleted:
                // 容忍性
                conf.setConf("");
                cc = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                // 节点数据变更了，再取一遍数据
                zk.getData("/AppConf",this,this,"ctx");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }
    }
}
