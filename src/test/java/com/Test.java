package com;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangling
 * @since 2020/11/9 13:58
 */
public class Test {

    static Thread a = null,b = null,c = null;

    public static void main(String[] args) {
        char[] aI = "AAA".toCharArray();
        char[] bI = "BBB".toCharArray();
        char[] cI = "CCC".toCharArray();


        a = new Thread(()->{
            for (;;) {
                System.out.print("A");
                LockSupport.unpark(b);
                LockSupport.park();
            }
        },"a");

        b = new Thread(()->{
            for (;;) {
                LockSupport.park();
                System.out.print("B");
                LockSupport.unpark(c);
            }
        },"b");

        c = new Thread(()->{
            for (;;) {
                LockSupport.park();
                System.out.print("C");
                LockSupport.unpark(a);
            }
        },"c");

        a.start();
        b.start();
        c.start();

    }
}
