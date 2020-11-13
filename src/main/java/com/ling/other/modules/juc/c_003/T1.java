/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * @author mashibing
 */

package com.ling.other.modules.juc.c_003;

public class T1 {

    private int count = 10;

    public synchronized void m() { //等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void n() { //访问这个方法的时候不需要上锁

        count++;
        System.out.println(Thread.currentThread().getName() + " Count = " + count);
    }

    public static void main(String[] args) {
        T1 t = new T1();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                t.m();
                //t.n();
            }).start();
        }
    }
}
