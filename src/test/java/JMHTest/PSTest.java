package JMHTest;

import org.openjdk.jmh.annotations.*;


public class PSTest {

    /**
     * @Warmup：预热，由于JVM中对特定代码会存在优化（本地化），所以JVM第一次启动执行效率会慢，JVM启动后，后面执行效率可能会更快
     * iterations调用1次，每隔3s迭代1次
     * @Fork：总共用多少个线程执行
     * @BenchmarkMode：基准测试的模式，这里测的是吞吐量（）Throughput
     * @Measurement：总共执行多少次测试（一个方法调几遍），每隔3s调用1边
     */

    @Benchmark
    @Warmup(iterations = 1,time = 3)
    @Fork(5)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1,time = 3)
    public void testForEach() {

        PS.foreach();

    }
}