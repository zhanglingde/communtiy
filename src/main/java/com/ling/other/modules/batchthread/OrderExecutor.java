package com.ling.other.modules.batchthread;

import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.SpringContextUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangling 2021/3/11 10:17
 */
@Data
public class OrderExecutor<T> {

    private static final Logger logger = LoggerFactory.getLogger(OrderExecutor.class);

    /**
     * 单个线程处理数量
     */
    private int singleCount;
    /**
     * 处理的总数据量
     */
    private int listSize;
    /**
     * 开启的线程数
     */
    private int runSize;
    /**
     * 操作的数据集
     */
    private List<T> list;

    private CountDownLatch begin, end;

    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 是否存在异常
     */
    private AtomicReference<Boolean> isError = new AtomicReference<>(false);

    /**
     * 回调函数
     */
    private CallBack callBack;

    private Random random = new Random();

    /**
     * 事务管理器
     */
    private PlatformTransactionManager transactionManager;

    public OrderExecutor(int singleCount, List<T> list) {
        if (singleCount <= 0 || CollectionUtils.isEmpty(list)) {
            throw new RrException("参数错误");
        }
        transactionManager = SpringContextUtils.getBean(PlatformTransactionManager.class);
        this.singleCount = singleCount;
        this.list = list;
        this.listSize = list.size();
        this.runSize = (this.listSize % this.singleCount) == 0 ? this.listSize / this.singleCount : this.listSize / this.singleCount + 1;

    }

    /**
     * 回调定义接口
     * @param <T>
     */
    public interface CallBack<T> {
        public void method(List<T> list);
    }

    public void execute() throws Exception {
        executorService = Executors.newFixedThreadPool(runSize);
        begin = new CountDownLatch(1);
        end = new CountDownLatch(runSize);

        //
        int startIndex = 0;
        int endIndex = 0;
        List<T> newList = null;
        for (int i = 0; i < runSize; i++) {
            //计算每个线程对应的数据
            if (i < (runSize - 1)) {
                startIndex = i * singleCount;
                endIndex = (i + 1) * singleCount;
                newList = list.subList(startIndex, endIndex);
            } else {
                startIndex = i * singleCount;
                endIndex = listSize;
                newList = list.subList(startIndex, endIndex);
            }
            //创建线程类处理数据
            MyThread<T> myThread = new MyThread(newList, begin, end) {
                @Override
                public void method(List list) {
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    TransactionStatus status = transactionManager.getTransaction(def);
                    //具体执行逻辑交给回调函数
                    try {
                        callBack.method(list);
                        /*if (random.nextInt(2) == 1) {
                            throw new RuntimeException("模拟异常抛出错误回滚");
                        }*/
                        logger.warn("多线程事务批量操作执行成功,线程名:{},操作成功数量:{}", Thread.currentThread().getName(), list.size());
                    } catch (Exception e) {
                        // 接收异常,处理异常
                        isError.set(true);
                        //e.printStackTrace();
                        logger.error("多线程事务批量操作抛错,线程名:{},操作失败数量:{},报错信息:{},{}", Thread.currentThread().getName(), list.size(), e.toString(), e);
                    }
                    //计数器减一
                    end.countDown();
                    try {
                        //等待所有线程任务完成，监控是否有异常，有则统一回滚
                        //log.warn("等待所有任务执行完成,当前时间:{},当前end计数:{}", LocalDateTime.now(), end.getCount());
                        end.await();
                        //log.warn("完成所有任务,当前时间:{},当前end计数:{}", LocalDateTime.now(), end.getCount());
                        if (isError.get()) {
                            // 事务回滚
                            transactionManager.rollback(status);
                        } else {
                            //事务提交
                            transactionManager.commit(status);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //执行线程
            executorService.execute(myThread);
        }
        //计数器减一，开始执行任务  begin此时为0
        begin.countDown();//
        //等待任务全部执行完毕，变为0则任务全部完成
        end.await();
        //关闭线程池
        executorService.shutdown();
        //不抛错也是可以回滚的
        /*if (isError.get()) {
            // 主线程抛出自定义的异常
            throw new RuntimeException("主线程抛出模拟异常");
        }*/
    }

}


