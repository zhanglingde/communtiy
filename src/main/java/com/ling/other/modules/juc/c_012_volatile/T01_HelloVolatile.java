/**
 * volatile 关键字，使一个变量在多个线程间可见
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 * <p>
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 * <p>
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 * <p>
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 * <p>
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 *
 * @author mashibing
 */
package com.ling.other.modules.juc.c_012_volatile;

import java.util.concurrent.TimeUnit;


/**
 * t1线程读取到running为true，主线程睡1s后修改running为false，t1线程仍是true，进行自旋
 */
public class T01_HelloVolatile {
    /*volatile*/ boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别
    int flag = 0;

    void m() {
        System.out.println("m start");
        while (true) {
            // 主线程改了running后，t1读取到的running值也改了

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("t1:" + running);
            System.out.println("t1:" + flag);
        }
        // 循环中没有代码时，running被主线程修改了，t1不知道，但是当循环中有代码时，running被修改了，t1线程读取到了修改后的值
        //while (running) {
        //    //int i = 1;
        //    //try {
        //    //    TimeUnit.SECONDS.sleep(1);
        //    //} catch (InterruptedException e) {
        //    //    e.printStackTrace();
        //    //}
        //    //System.out.println("t1:" + running);
        //}

        //System.out.println("m end!");
    }

    public static void main(String[] args) {
        T01_HelloVolatile t = new T01_HelloVolatile();

        new Thread(t::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.flag++;
            //System.out.println("main:" + t.running);
        }
    }

}


