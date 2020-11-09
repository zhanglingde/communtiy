package com.ling.other.modules.juc.c_025;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 *
 * offer：添加元素到队列，添加成功返回true，添加失败返回false；相当与List中的add（但是list中的add添加失败会抛异常）
 * poll队列中取，并移除队列中的元素
 * pell队列中取，但不会移除队列中的元素
 */
public class T04_ConcurrentQueue {
	public static void main(String[] args) {
		Queue<String> strs = new ConcurrentLinkedQueue<>();
		
		for(int i=0; i<10; i++) {
			strs.offer("a" + i);  //add
		}
		
		System.out.println(strs);
		
		System.out.println(strs.size());
		
		System.out.println(strs.poll());
		System.out.println(strs.size());
		
		System.out.println(strs.peek());
		System.out.println(strs.size());
		
		//双端队列Deque
	}
}
