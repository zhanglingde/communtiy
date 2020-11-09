/**
 * http://blog.csdn.net/sunxianghuang/article/details/52221913 
 * http://www.educity.cn/java/498061.html
 * 阅读concurrentskiplistmap
 */
package com.ling.other.modules.juc.c_025;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * ConcurrentHashMap是无序的，
 * ConcurrentSkipListMap是有序的，因为TreeMap是使用红黑树实现的，对TreeMap使用CAS操作太复杂了，所以TreeMap没有高并发操作类，
 * ConcurrentSkipListMap的排序是使用跳表的方式实现的，使用CAS比TreeMap简单
 */
public class T01_ConcurrentMap {
	public static void main(String[] args) {
		Map<String, String> map = new ConcurrentHashMap<>();
		//Map<String, String> map = new ConcurrentSkipListMap<>(); //高并发并且排序,使用跳表的方式实现链表的快速查找
		
		//Map<String, String> map = new Hashtable<>();
		//Map<String, String> map = new HashMap<>(); //Collections.synchronizedXXX
		//TreeMap
		Random r = new Random();
		Thread[] ths = new Thread[100];
		CountDownLatch latch = new CountDownLatch(ths.length);
		long start = System.currentTimeMillis();
		for(int i=0; i<ths.length; i++) {
			ths[i] = new Thread(()->{
				for(int j=0; j<10000; j++) map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
				latch.countDown();
			});
		}
		
		Arrays.asList(ths).forEach(t->t.start());
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(map.size());

	}
}
