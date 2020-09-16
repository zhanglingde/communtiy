package com.reflect;

import java.lang.reflect.Constructor;

public class Test {
    public static void main(String[] args) throws Exception {

        Class co = Class.forName("com.ling.other.modules.easyexcel.vo.PoLineVO");
        Constructor constructor = co.getConstructor();

        Class c = Class.forName("com.reflect.Person");
        Constructor con = c.getConstructor();
        Person o1 = (Person) con.newInstance();
        Person o = (Person) c.newInstance();
        o.setName("zhang");
        o1.setAge(18);
        System.out.println(o);
        System.out.println(o1);

    }
}

class Person{
    String name;
    int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
