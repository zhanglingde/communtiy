package com.ling.community;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/8/21 9:18
 */
public class Demo {

    /**
     * 泛型方法
     * @param t
     * @param <T>
     * @return  集合
     */
    public static <T> List<T> test(List<T> t){
        return t;
    }

    // 没有返回值
    public static <T> void show (T t){
        System.out.println(t);
    }

    public static void main(String[] args) {

        List<String> strings = Demo.test(Arrays.asList("a","b","c"));
        strings.forEach(System.out::println);

        List<Integer> integerList = Demo.test(Arrays.asList(1,2,3));
        integerList.forEach(System.out::println);


        //=======================================
        Demo.show("hello");
        Demo.show(56);
        Demo.show(Boolean.TRUE);
    }
}

class Generic<T>{
    private T t;

}
