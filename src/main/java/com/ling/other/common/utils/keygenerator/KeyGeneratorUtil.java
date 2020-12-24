package com.ling.other.common.utils.keygenerator;


/**
 * 生成主键 主键按照 时间戳+workid+递增数18位数字组成
 * @author nanziyu
 * @date 2020/7/2 9:55
 */
public class KeyGeneratorUtil {
    private static final KeyGenerator KEY_GENERATOR = new KeyGenerator();

    static {
        KeyGenerator.setWorkerId(getWorkerId());
    }


    public static KeyGenerator getGenerator() {
        return KEY_GENERATOR;
    }

    private static int getWorkerId() {
        //String ip = IpUtils.getServerInstanceName();
        String ip = "1";
        int shift = (int) (1L << KeyGenerator.WORKER_ID_BITS);
        int mod = ip.hashCode() % shift;
        if (0 > mod) {
            mod += shift;
        }
        return mod;
    }
}