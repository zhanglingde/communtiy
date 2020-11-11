package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * newCachedThreadPool:如果线程中有线程存在，没有到60s线程消息，用现有线程执行任务；如果线程池中没有线程，来一个新任务，启一个新线程
 * 任务队列使用的是SynchronousQueue，SynchronousQueue容量为0，添加一个元素到队列中，必须有线程取走，否则队列会阻塞住
 *
 * 最大线程数是Integer.MAX_VALUE,如果线程数要达到这么多，那么CPU就光在线程间切换调用了，所以一般不用该线程池，除非事先确定任务不会堆积，线程数不多
 */
public class T08_CachedPool {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println(service);
		for (int i = 0; i < 2; i++) {
			service.execute(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("线程名："+Thread.currentThread().getName());
			});
		}
		System.out.println(service);
		
		TimeUnit.SECONDS.sleep(80);
		
		System.out.println(service);
		
		
	}
}
