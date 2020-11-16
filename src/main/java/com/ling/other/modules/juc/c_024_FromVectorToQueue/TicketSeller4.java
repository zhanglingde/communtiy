
package com.ling.other.modules.juc.c_024_FromVectorToQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 多线程使用ConcurrentQueue提高并发性，不使用List
 * 多线程不重复的元素可以使用ConcurrentSet
 */
public class TicketSeller4 {
	static Queue<String> tickets = new ConcurrentLinkedQueue<>();
	
	
	static {
		for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
	}
	
	public static void main(String[] args) {
		
		for(int i=0; i<10; i++) {
			new Thread(()->{
				while(true) {
					// poll()取队列中头个元素，并移除，如果取到null返回null
					// 原子性的，使用cas操作，无锁
					String s = tickets.poll();
					if(s == null) break;
					else System.out.println("销售了--" + s);
				}
			}).start();
		}
	}
}
