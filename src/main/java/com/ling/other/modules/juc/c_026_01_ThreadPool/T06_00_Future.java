/**
 * 认识future
 * 异步
 */
package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T06_00_Future {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
  
		// FutureTask即是个Runnable任务，也是个Future,作为任务执行完结果存在自己本身
		FutureTask<Integer> task = new FutureTask<>(()->{
			TimeUnit.MILLISECONDS.sleep(500);
			return 1000;
		}); //new Callable () { Integer call();}

		new Thread(task).start();

		System.out.println(task.get()); //阻塞

		//ExecutorService service = Executors.newFixedThreadPool(5);
		//Future<Integer> f = service.submit(() -> {
		//	try {
		//		TimeUnit.SECONDS.sleep(500);
		//	} catch (InterruptedException e) {
		//		e.printStackTrace();
		//	}
		//	return 1;
		//});
		//System.out.println(f.get());
		//System.out.println(f.isDone());


	}
}
