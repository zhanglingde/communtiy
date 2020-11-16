package com.ling.other.modules.juc.c_023_02_FromHashtableToCHM;

import java.util.Hashtable;
import java.util.UUID;

/**
 * 100个线程同时向容器中放1000000个UUID，每个线程放10000个UUID，测试写和读的效率
 */
public class T01_TestHashtable {

    static Hashtable<UUID, UUID> m = new Hashtable<>();

    // count = 1000000
    static int count = Constants.COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];
    // thread_count = 100
    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        // 每一个线程放多少个数：10000个数
        int gap = count/THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for(int i=start; i<start+gap; i++) {
                m.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        // 100个线程，线程1放0-9999，线程2放100000-19999
        for(int i=0; i<threads.length; i++) {
            threads[i] = new MyThread(i * (count/THREAD_COUNT));
        }

        for(Thread t : threads) {
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("运行时间:"+ (end - start));

        System.out.println(m.size());

        //---------------读--------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                // 100个线程，每个线程取10000000万次第10个元素
                for (int j = 0; j < 10000000; j++) {
                    m.get(keys[10]);
                }
            });
        }

        for(Thread t : threads) {
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
