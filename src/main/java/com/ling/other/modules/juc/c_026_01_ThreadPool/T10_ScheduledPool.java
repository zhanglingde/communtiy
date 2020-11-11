package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务线程池：quartz,cron
 */
public class T10_ScheduledPool {
	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
		/**
		 * 隔多长时间执行一次
		 * initialDelay：第一次任务执行之前隔多长时间执行
		 * period：第一次任务后，多长时间执行一次
		 */
		service.scheduleAtFixedRate(()->{
			try {
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}, 0, 500, TimeUnit.MILLISECONDS);
		
	}
}
