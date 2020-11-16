/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * 
 * 分析下面的程序可能会产生哪些问题？
 *  
 * 使用Vector或者Collections.synchronizedXXX
 * 分析一下，这样能解决问题吗？
 * 
 * @author 马士兵
 */
package com.ling.other.modules.juc.c_024_FromVectorToQueue;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 线程不安全，不能保证原子性
 */
public class TicketSeller2 {
	static Vector<String> tickets = new Vector<>();
	
	
	static {
		for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
	}
	
	public static void main(String[] args) {
		
		for(int i=0; i<10; i++) {
			new Thread(()->{
				while(tickets.size() > 0) {

					// size方法加了锁，但是这段代码没有加锁，所以很多线程都判断size大于0，但是只剩一张票了
					//try {
					//	TimeUnit.MILLISECONDS.sleep(10);
					//} catch (InterruptedException e) {
					//	e.printStackTrace();
					//}

					System.out.println("销售了--" + tickets.remove(0));
				}
			}).start();
		}
	}
}
