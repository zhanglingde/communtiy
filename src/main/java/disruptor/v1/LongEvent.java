package disruptor.v1;

/**
 * 事件，消息：可以装任何值，放到RingBuffer中的是LongEvent类型
 */
public class LongEvent {
    // 可以
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
