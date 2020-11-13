
package com.ling.other.modules.juc.c_010;

import java.util.concurrent.TimeUnit;

/**
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法，锁的都是this也是可重入的
 *
 */
public class T {
	synchronized void m() {
		System.out.println("m start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m end");
	}
	
	public static void main(String[] args) {
		new TT().m();
	}
	
}

class TT extends T {

	// 锁
	@Override
	synchronized void m() {
		System.out.println("child m start");
		super.m();
		System.out.println("child m end");
	}
}
