package disruptor.v1;

import com.lmax.disruptor.EventFactory;

/**
 * Event工厂：用于产生事件，创建LongEvent消息
 *
 * 这里为什么用工厂而不用直接创建Event：牵扯到效率问题：disruptor初始化的时候，会调用Event工厂，对ringBuffer进行内存的提前分配，
 * 当有Event产生的时候，直接修改覆盖ringBuffer中的值，不用重新创建，GC频率会降低
 *
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    public LongEvent newInstance() {
        return new LongEvent();
    }
}
