package com.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangling
 * @since 2020/9/7 10:28
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Class clazz = SmallPineapple.class;
        Constructor con = clazz.getConstructor();

        Annotation[] annotations = con.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.toString());
        }

        Object o = con.newInstance();

        Method show = clazz.getMethod("show");
        show.invoke(o);

        Annotation[] annotations1 = show.getAnnotations();
        for (Annotation annotation : annotations1) {
            System.out.println(annotations1.toString());
        }

        //Field name = clazz.getField("name");
        //Annotation[] annotations2 = name.getAnnotations();
        //for (Annotation annotation : annotations2) {
        //    System.out.println(annotation.toString());
        //    Name n = (Name)annotation;
        //    System.out.println(n.value());
        //}

        Field[] fields = clazz.getDeclaredFields();
        Set<Field> allFields = new HashSet<>();
        allFields.addAll(Arrays.asList(fields));
        for (Field field : allFields) {
            Annotation[] names = field.getAnnotations();
            for (Annotation annotation : names) {
                Name n = (Name)annotation;
                System.out.println(n.value());
            }
            Name na = field.getAnnotation(Name.class);
            if (na != null) {
                System.out.println(na.value());
            }

        }

        List<Field> fields1 = Stream.of(clazz.getDeclaredFields()).collect(Collectors.toList());

    }
}
