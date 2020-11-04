package com.ling.other.modules.juc.c_020;

import java.util.concurrent.Exchanger;

/**
 * Exchanger：两个线程间交换值
 */
public class T12_TestExchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s = "T1";
            try {
                // 该方法是阻塞的
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t1").start();


        new Thread(()->{
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t2").start();


    }
}
