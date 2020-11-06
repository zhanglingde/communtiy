package com.ling.other.modules.juc.c_022_RefTypeAndThreadLocal;

import java.io.IOException;

public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        // 当m一直指向new M()时不会被回收，当m= null时，m会被回收
        m = null;
        System.gc(); //DisableExplicitGC

        // gc是运行在其他线程中的，需要阻塞当前线程，让gc执行
        System.in.read();
    }
}
