
package com.ling.other.modules.juc.c_020;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock还可以指定为公平锁
 */
public class T05_ReentrantLock5 extends Thread {

    // 默认是非公平锁
    //参数为true表示为公平锁，请对比输出结果
	//private static ReentrantLock lock=new ReentrantLock(true);
	Lock lock=new ReentrantLock(false);
    public void run() {
        for(int i=0; i<100; i++) {
            lock.lock();
            try{
                System.out.println(i+Thread.currentThread().getName()+"获得锁");
            }finally{
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        T05_ReentrantLock5 rl=new T05_ReentrantLock5();
        Thread th1=new Thread(rl);
        Thread th2=new Thread(rl);
        th1.start();
        th2.start();
    }
}
