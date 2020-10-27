package com.ling.other.modules.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper连接创建节点
 *
 * @author zhangling
 * @since 2020/10/27 9:44
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        //zookeeper是有session概念的，没有连接池的概念
        // Watch:观察，回调
        // 第一类：new zk的时候，传入的watch，这个watch，session级别的，跟path、node没有关系
        CountDownLatch cd = new CountDownLatch(1);// CountDownLatch是线程安全的
        ZooKeeper zk = new ZooKeeper("192.168.191.128:2181,192.168.191.129:2181,192.168.191.130:2181,192.168.191.132:2181",
                3000, new Watcher() {
            // Watch的回调方法
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                String path = watchedEvent.getPath();
                System.out.println("new zk watch:" + watchedEvent.toString());


                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println("sync connected...");
                        cd.countDown();
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                    case Closed:
                        break;
                }

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
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
        });

        // zookeeper中的方法是异步的，所以需要先阻塞住，等SyncConnected连接完后，才在这里放开阻塞
        cd.await();
        ZooKeeper.States state = zk.getState();
        switch (state) {
            // 正在连接zookeeper
            case CONNECTING:
                System.out.println("ing...");
                break;
            case ASSOCIATING:
                break;
            //  连接成功
            case CONNECTED:
                System.out.println("ed...");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        String pathName = zk.create("/ooxx", "oldData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Stat stat = new Stat();
        byte[] node = zk.getData("/ooxx", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getData watch: " + event.toString());
                try {
                    zk.getData("/ooxx",this,stat);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);

        System.out.println(new String(node));

        // 触发回调
        Stat stat1 = zk.setData("/ooxx", "newData".getBytes(), 0);
        Stat stat2 = zk.setData("/ooxx", "newdata01".getBytes(), stat1.getVersion());

        // 异步回调
        System.out.println("----------------async start------------------");
        zk.getData("/ooxx", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("----------------async call back------------------");
                System.out.println(ctx.toString());
                System.out.println(new String(data));
            }
        },"abc");
        System.out.println("----------------async over------------------");

        Thread.sleep(2222222);

    }
}
