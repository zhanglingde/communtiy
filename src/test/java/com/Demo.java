package com;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author zhangling
 * @since 2020/10/28 13:39
 */
public class Demo {

    @Test
    public void demo01(){
        BigDecimal a = new BigDecimal(88);
        BigDecimal b = new BigDecimal(12);


        BigDecimal c = a.negate().add(b.negate());
        BigDecimal sum = a.negate().multiply(BigDecimal.valueOf(2));
        System.out.println(sum);
        System.out.println("c:"+c);
    }
}
