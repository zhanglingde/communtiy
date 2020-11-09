package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.concurrent.Executor;

/**
 * 认识Executor，执行器：定义和运行分开
 *
 * 之前线程需要new Thread()，然后重写run(),调用start()方法才可以运行；或是重写了Runnable，也必须new Thread(),定义和运行是固定的，写死的
 * 可以自己定义运行的方式，实现Exector的接口方式不一样
 *
 */
public class T01_MyExecutor implements Executor{

	public static void main(String[] args) {
		new T01_MyExecutor().execute(()->System.out.println("hello executor"));
	}

	@Override
	public void execute(Runnable command) {
		//new Thread(command).run();
		command.run();
		
	}

}

