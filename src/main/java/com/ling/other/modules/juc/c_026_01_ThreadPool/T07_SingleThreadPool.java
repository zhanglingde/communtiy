package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newSingleThreadExecutor:线程池中只有一个线程
 * 线程池有任务队列，还有完整的生命周期
 */
public class T07_SingleThreadPool {
	public static void main(String[] args) {
		// 所有线程池是从ExecutorServer继承的，Executors是对线程池的工厂
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			final int j = i;
			service.execute(()->{
				
				System.out.println(j + " " + Thread.currentThread().getName());
			});
		}
			
	}
}
