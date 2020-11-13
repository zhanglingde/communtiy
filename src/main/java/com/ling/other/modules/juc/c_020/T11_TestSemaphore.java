package com.ling.other.modules.juc.c_020;

import java.util.concurrent.Semaphore;

/**
 * 信号灯：灯亮了就能执行，灯没亮就不能执行
 */
public class T11_TestSemaphore {
    public static void main(String[] args) {
        // 默认是非公平，传true表示公平锁
        //Semaphore s = new Semaphore(2, true);
        //允许一个线程同时执行
        Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                // 获得不到就阻塞住；获得1个，Semaphore减1，Semaphore减到0其他线程acquire不到就不能运行了
                s.acquire();

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Semaphore加1
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                s.release();
            }
        }).start();
    }
}
