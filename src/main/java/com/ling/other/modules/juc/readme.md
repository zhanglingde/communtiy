1. `juc`：m1同步方法

- `c_009`：synchronized可重入
- `c_010`：子类调用父用，锁的都是this也是可重入的
- `c_011`：程序在执行过程中，如果出现异常，默认情况锁会被释放,如果不想被释放，可以捕获异常

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




### Synchronized的底层实现
- 在JDK早期是重量级的，即需要向OS申请锁，效率低
- 后期的改进：锁升级

sync(Object)
第一个线程访问的时候，在Object的markword记录这个线程的线程id（偏向锁），默认没有其他线程争抢锁
如果有线程争用，升级为自旋锁，在临界资源边界while（true）循环，等待，（默认自旋10次）
> 自旋锁占CPU，不访问操作系统，所以是在用户态，没有向内核态转变，
10次以后，升级为重量级锁，向操作系统申请资源，(Atomicl,Lock)
> 锁只能升级，不能降级
>
> - 加锁代码执行时间短的，线程数少的使用自旋锁，如果线程数多，20000个线程在自旋等待
> - 执行时间长，线程数多使用系统锁
>
>>

