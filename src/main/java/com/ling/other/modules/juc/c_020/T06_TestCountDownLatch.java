package com.ling.other.modules.juc.c_020;

import java.util.concurrent.CountDownLatch;

// 门闩：CountDownLatch
public class T06_TestCountDownLatch {
    public static void main(String[] args) {
        usingJoin();
        usingCountDownLatch();
    }

    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        // 门闩
        CountDownLatch latch = new CountDownLatch(threads.length);

        // 线程内容
        for(int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++) result += j;
                // 线程结束时latch.countDown，门闩里面的值减1
                latch.countDown();
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        try {
            // 门闩阻塞住，当latch变为0时，就打开了
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end latch");
    }

    /**
     * 用join方法也可以阻塞住线程，让线程执行完，但是使用CountDownLatch更灵活
     */
    private static void usingJoin() {
        Thread[] threads = new Thread[100];

        for(int i=0; i<threads.length; i++) {
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++) result += j;
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("end join");
    }
}
