package com.ling.other.modules.juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park()可能让线程阻塞，不需要加锁，而wait()需要加锁；
 * LockSupport.unpark()可以唤醒某个指定的线程，notify()不能唤醒等待队列中的指定线程
 */
public class T13_TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5) {
                    // 当前线程停止，阻塞
                    LockSupport.park();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        // 唤醒某个线程t，unpark可以先于park调用，主线程先调用unpark,线程t调用park不会阻塞
        LockSupport.unpark(t);

        /*try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 8 senconds!");
        //  主线程到8s后继续运行
        LockSupport.unpark(t);*/

    }
}
