package com.ling.other.common.utils.keygenerator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创造主键
 * @author nanziyu
 * @date 2020/7/2 9:49
 */
public final class KeyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(KeyGenerator.class);


    public static final long EPOCH;

    private static final long SEQUENCE_BITS = 10L;

    public static final long WORKER_ID_BITS = 12L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static long workerId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.OCTOBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
        logger.info("KeyGenerator Date:" + calendar.getTime() + ", EPOCH: " + EPOCH);
    }

    private long sequence;

    private long lastTime;

    /**
     * Set work process id.
     *
     * @param workerId work process id
     */
    public static void setWorkerId(final long workerId) {
        if (workerId < 0L || workerId >= WORKER_ID_MAX_VALUE) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0: %d", WORKER_ID_MAX_VALUE - 1, workerId));
        }
        KeyGenerator.workerId = workerId;
        logger.info("workerId:" + workerId);
    }

    /**
     * Generate key.
     *
     * @return key type is @{@link Long}.
     */
    public synchronized long nextKey() {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis < lastTime) {
            throw new RuntimeException(String.format("Clock is moving backwards.  Refusing to generate id for %d milliseconds", lastTime - currentMillis));
        } else if (currentMillis == lastTime) {
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        lastTime = currentMillis;
        if (logger.isDebugEnabled()) {
            logger.debug("{}-{}-{}"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime)).toString()+ workerId+ sequence);
        }
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }
}