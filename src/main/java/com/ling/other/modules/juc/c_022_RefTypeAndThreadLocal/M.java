package com.ling.other.modules.juc.c_022_RefTypeAndThreadLocal;

public class M {

    @Override
    protected void finalize() throws Throwable {
        // 输出finalize表示该对象被回收了
        System.out.println("finalize");
    }
}
