package com.ling.other.modules.juc.c_026_00_interview;


public class T03_00_cas {

    // 防止取别的值，严谨
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T2; //思考为什么必须volatile

    public static void main(String[] args) {

        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(() -> {

            for (char c : aI) {
                // r ！= T1时，自旋等待
                while (r != ReadyToRun.T1) {}
                System.out.print(c);
                r = ReadyToRun.T2;
            }

        }, "t1").start();

        new Thread(() -> {

            for (char c : aC) {
                while (r != ReadyToRun.T2) {}
                System.out.print(c);
                r = ReadyToRun.T1;
            }
        }, "t2").start();
    }
}


