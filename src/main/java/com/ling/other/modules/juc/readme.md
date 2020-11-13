
1. `c_000`

    > - 创建线程
    > - sleep,yield,join方法
    > - 线程状态
2. `c_004`
    
    锁静态方法： `public synchronized static void m()`

3. `c_007`

    同步方法和非同步方法之间的调用 
3. `c_008`
    
    面试题
    
4. `c_009`：synchronized可重入锁  
5. `c_010`：子类调用父用的同步方法，锁的都是this也是可重入的
6. `c_011`：程序在执行过程中，如果出现异常，默认情况锁会被释放,如果不想被释放，可以捕获异常
 
    锁升级
    
#### volatile

1. T01_HelloVolatile，循环中有代码，main线程改了值，线程1可见，为什么？
2. cas ,sync, longadder



- `c_023_02`：HashTable,HashMap,ConcurrentHashMap效率对比
    1. Vector,Hashtable：自带锁，基本不用
    2. HashTable,SynchronizedHashMap,ConcurrentHashMap都是有锁的，三者在插入操作上效率差距不是特别大，但是读取上ConcurrentHashMap效率会高很多
    
    
    
- `juc.c_024`：Vector到Queue
    多线程的不用List，先想到Queue，用Set也可以，set有Concurrent的

> Queue和List的区别
> Queue提供了很多对线程友好的API offer,peek,poll等
> BlockingQueue 还提供了put take阻塞的接口（生产者/消费者模型）
    
# c_020 ReentrantLock
5. 公平锁



