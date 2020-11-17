package com.ling.other.modules.juc.c_025;

import java.util.concurrent.LinkedTransferQueue;

/**
 * transfer()方法：线程添加数据到队列后，就在那等着，阻塞住，直到该元素被取走，在释放阻塞
 */
public class T09_TransferQueue {
	public static void main(String[] args) throws InterruptedException {
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

		// 消费者线程
		new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		// 线程添加数据到队列后，就在那等着，阻塞住，直到该元素被取走，在释放阻塞
		strs.transfer("aaa");
		// 线程往队列中添加元素，如果队列满了会阻塞住，如果没有满添加完线程就走了
		//strs.put("aaa");


		/*new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();*/


	}
}
