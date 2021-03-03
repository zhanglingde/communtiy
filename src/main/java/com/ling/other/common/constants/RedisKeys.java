package com.ling.other.common.constants;

/**
 * 枚举类型
 * @author zhangling 2020/8/24 11:09
 */
public enum RedisKeys {

    /**
     * lov 设置过期时间1周
     */
    lov("lov:lov:",604800),
    /**
     * lovValue 设置过期时间1周
     */
    lovValue("lov:value:",604800),

    /**
     * 币种税率：过期时间30分钟
     */
    currency("currency:tax_rate:",1800);

    private long expire;
    private String key;

    RedisKeys(String key,long expire) {
        this.expire = expire;
        this.key = key;
    }

    public long getExpire() {
        return expire;
    }

    public String getKey() {
        return key;
    }




}
