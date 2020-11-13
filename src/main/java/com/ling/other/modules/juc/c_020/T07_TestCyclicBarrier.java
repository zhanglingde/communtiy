package com.ling.other.modules.juc.c_020;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏：CyclicBarrier阻塞住，满到20放开阻塞，线程堆满后执行
 */
public class T07_TestCyclicBarrier {
    public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(20);

        // 调用await后，barrier+1，当到20后，await放开阻塞，
        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人"));

        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                // 到20后执行一次该方法
                System.out.println("满人，发车");
            }
        });*/

        for(int i=0; i<100; i++) {
                final int a = i;
                // 创建100个线程
                new Thread(()->{
                    try {
                        // 阻塞住，到20放开阻塞
                        barrier.await();
                        System.out.println(a);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            
        }
    }
}
