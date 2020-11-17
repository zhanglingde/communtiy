package com.ling.other.modules.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

/**
 * 认识Callable，对Runnable进行了扩展
 * 对Callable的调用，可以有返回值
 */
public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        // 把任务交给线程池运行
        Future<String> future = service.submit(c); //异步

        System.out.println(future.get());//阻塞，获取到结果后，返回放开阻塞

        service.shutdown();
    }

}
