package com.ling.other.modules.juc.c_025;

import java.util.PriorityQueue;

/**
 * PriorityQueue:优先级队列，取时排序；
 * 内部实现了排序，底层实现是二叉树，堆排序（最小堆，小顶堆）
 */
public class T07_01_PriorityQueque {
    public static void main(String[] args) {
        PriorityQueue<String> q = new PriorityQueue<>();

        q.add("c");
        q.add("e");
        q.add("a");
        q.add("d");
        q.add("z");

        for (int i = 0; i < 5; i++) {
            System.out.println(q.poll());
        }

    }
}
