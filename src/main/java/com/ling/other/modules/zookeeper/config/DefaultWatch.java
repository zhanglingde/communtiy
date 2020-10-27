package com.ling.other.modules.zookeeper.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.nio.file.Watchable;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling
 * @since 2020/10/27 13:44
 */
public class DefaultWatch implements Watcher {

    CountDownLatch cd;

    public CountDownLatch getCd() {
        return cd;
    }

    public void setCd(CountDownLatch cd) {
        this.cd = cd;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        System.out.println(watchedEvent.toString());

        switch (watchedEvent.getState()) {
            case Unknown:
                break;
            case Disconnected:
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                // 连接成功后放开阻塞
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

    }
}
