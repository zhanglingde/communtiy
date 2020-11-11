package disruptor.v1;

import com.lmax.disruptor.EventHandler;

/**
 * 消息的消费者：
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    /**
     *
     * @param longEvent 哪个消息
     * @param l 哪个位置的消息
     * @param b 是否处理完了，消息是否结束，true处理完了，消费者退出了；false继续消费
     * @throws Exception
     */
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }
}
