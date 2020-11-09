package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.io.IOException;
import java.util.concurrent.*;

public class T05_00_HelloThreadPool {

    // 每个任务打印任务id
    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Task " + i);
            try {
                System.in.read();// 阻塞
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {
        /**
         * 核心线程数：最开始的时候线程池中的核心线程
         * 最大线程数：线程数不够了，能够扩展到的最大的线程数量（核心线程+非核心线程）
         * 生存时间：线程长时间不工作了，把它归还给操作系统（最大线程4个，当两个线程长时间不工作了，非核心线程归还给操作系统，2个核心线程永远不归还（有参数设置，但一般不设置））
         * 生存时间单位、
         * 任务队列：不同的Queue就会产生不同的线程池，ArrayBlockingQueue,LinkedBlockingQueue(任务最大数Integer最大值),SynchronizedQueue
         * 线程工厂（产生什么样的线程，线程名是什么）：
         * 拒绝策略：任务进线程池，核心线程数执行；当核心线程数不够时，任务进入任务队列；当任务队列满了后，创建新线程执行任务，如果线程数超过了最大线程数，执行拒绝策略
         */
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 8; i++) {
            tpe.execute(new Task(i));
        }

        System.out.println(tpe.getQueue());

        tpe.execute(new Task(100));

        System.out.println(tpe.getQueue());

        tpe.shutdown();
    }
}
