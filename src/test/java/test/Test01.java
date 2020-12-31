package test;



import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ling.other.modules.scheduler.init.ExecutorInit;

import java.util.concurrent.*;

/**
 * @author zhangling
 * @since 2020/12/29 11:44
 */
public class Test01 {
    public static void main(String[] args) {
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("scheduler-register-runner-%d").build();
        // 定义执行定时任务的线程池
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), threadFactory);
        executor.execute(new Test01.Registry());
    }

    static class Registry implements Runnable{
        Registry(){}

        @Override
        public void run() {
            System.out.println("测试");

        }
    }
}
