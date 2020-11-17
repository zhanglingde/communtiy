package com;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangling
 * @since 2020/11/9 13:58
 */
public class Test {

    static Thread a = null,b = null,c = null;

    public static void main(String[] args) {
        TransferQueue<String> transfer = new LinkedTransferQueue<>();

        new Thread(()->{
            for(;;){
                try {
                    transfer.transfer("A");
                    System.out.print(transfer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
          for(;;){
              try {
                  System.out.print(transfer.take());
                  transfer.transfer("B");
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        }).start();
    }

    void test(){
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
