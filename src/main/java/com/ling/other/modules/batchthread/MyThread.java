package com.ling.other.modules.batchthread;

import com.ling.other.common.exception.RrException;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangling 2021/3/11 10:35
 */
public abstract class MyThread<T> implements Runnable{

    //list:总数据分割后某线程负责执行的数据
    private List<T> list;
    private CountDownLatch begin, end;

    public MyThread(List<T> list, CountDownLatch begin, CountDownLatch end) {
        this.list = list;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            begin.await();
            //执行程序
            method(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //throw new RrException(e);
        } finally {
            //计数器减一
            //end.countDown();
        }
    }

    public abstract void method(List<T> list);

}
