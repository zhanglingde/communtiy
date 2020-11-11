package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 底层使用ForkJoinPool实现的,流式API
 */
public class T13_ParallelStreamAPI {
	public static void main(String[] args) {
		List<Integer> nums = new ArrayList<>();
		Random r = new Random();
		for(int i=0; i<10000; i++) nums.add(1000000 + r.nextInt(1000000));
		
		//System.out.println(nums);
		
		long start = System.currentTimeMillis();
		// 流式处理
		nums.forEach(v->isPrime(v));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		//使用parallel stream api
		
		start = System.currentTimeMillis();
		// 并行流，并行处理，切成一个个小任务，底层实现是ForkJoinPool
		nums.parallelStream().forEach(T13_ParallelStreamAPI::isPrime);
		end = System.currentTimeMillis();
		
		System.out.println(end - start);
	}
	
	static boolean isPrime(int num) {
		for(int i=2; i<=num/2; i++) {
			if(num % i == 0) return false;
		}
		return true;
	}
}
