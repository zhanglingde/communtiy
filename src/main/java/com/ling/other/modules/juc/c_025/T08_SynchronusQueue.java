package com.ling.other.modules.juc.c_025;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 让一个线程给另一个线程下达任务的，相当与Exchange，一个线程给另一个线程传数据
 * 同步Queue，一个线程保证给另一个线程数据
 */
public class T08_SynchronusQueue { //容量为0
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> strs = new SynchronousQueue<>();
		
		new Thread(()->{
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		// 线程往队列中添加元素，如果队列满了会阻塞住，如果没有满添加完线程就走了
		strs.put("aaa"); //阻塞等待消费者消费
		//strs.put("bbb");
		//strs.add("aaa");
		System.out.println(strs.size());
	}
}
