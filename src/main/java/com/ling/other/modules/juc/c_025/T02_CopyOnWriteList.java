
package com.ling.other.modules.juc.c_025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 写时复制容器 copy on write
 * 多线程环境下，写时效率低，读时效率高
 * 适合写少读多的环境
 *
 * CopyOnWriteArrayList：写时加锁，读时不加锁
 *
 */
public class T02_CopyOnWriteList {
	public static void main(String[] args) {
		List<String> lists = new CopyOnWriteArrayList<>();
				//new ArrayList<>(); //这个会出并发问题！
				//new Vector();
		Random r = new Random();
		Thread[] ths = new Thread[100];
		
		for(int i=0; i<ths.length; i++) {
			Runnable task = new Runnable() {
	
				@Override
				public void run() {
					// 100个线程，每个线程进行1000次写操作
					for(int i=0; i<1000; i++) lists.add("a" + r.nextInt(10000));
				}
				
			};
			ths[i] = new Thread(task);
		}
		
		
		runAndComputeTime(ths);
		
		System.out.println("容器大小："+lists.size());
	}
	
	static void runAndComputeTime(Thread[] ths) {
		long s1 = System.currentTimeMillis();
		Arrays.asList(ths).forEach(t->t.start());
		Arrays.asList(ths).forEach(t->{
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		long s2 = System.currentTimeMillis();
		System.out.println("写操作时间："+(s2 - s1));
		
	}
}
