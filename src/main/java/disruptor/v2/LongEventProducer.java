package disruptor.v2;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;


    // 为Java8的lambda写法做准备（）

    /**
     *  把数据翻译成消息，Long类型的数据直接翻译成消息
     *  将值转换成LongEvent,带有一个参数，这个参数通过translateto方法转换成LongEvent
     *
     */
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {

            event.setValue(bb.getLong(0));
        }
    };

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {

        this.ringBuffer = ringBuffer;
    }

    /**
     *
     * @param buffer
     */
    public void onData(ByteBuffer buffer) {
        // 传TRANSLATOR，TRANSLATOR调用translateTo的方法
        ringBuffer.publishEvent(TRANSLATOR, buffer);
    }

}
