/**
 * 分析一下这个程序的输出
 *
 */
package com.ling.other.modules.juc.c_005;


// volatile不能保证线程安全，volatile+cas才能保证线程安全，因为volatile不能保证线程的原子性
public class T implements Runnable {


	private /*volatile*/ int count = 100;

	// count--和sout之间可能有其他线程操作，而且count--也不是原子的
	public /*synchronized*/ void run() { 
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		T t = new T();
		for(int i=0; i<100; i++) {
			new Thread(t, "THREAD" + i).start();
		}
	}
	
}
