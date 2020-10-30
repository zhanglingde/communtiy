
package com.ling.other.modules.juc.c_012_volatile;

import java.util.ArrayList;
import java.util.List;


/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized,不能保证原子性
 * 运行下面的程序，并分析结果
 *
 */
public class T04_VolatileNotSync {
	volatile int count = 0;

	// count++ 操作不是原子的，多个线程可能同时读取到count是同一个值1，都加完后是2
	void m() {
		for(int i=0; i<10000; i++) count++;
	}
	
	public static void main(String[] args) {
		T04_VolatileNotSync t = new T04_VolatileNotSync();
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i=0; i<10; i++) {
			threads.add(new Thread(t::m, "thread-"+i));
		}
		
		threads.forEach((o)->o.start());

		// 保证所有线程都结束
		threads.forEach((o)->{
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(t.count);

	}
	
}


