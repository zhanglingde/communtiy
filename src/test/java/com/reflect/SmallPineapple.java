package com.reflect;

import lombok.Data;

@Data
public class SmallPineapple {
    @Name("zhang")
    private String name;
    @Name("18")
    private int age;
    private double weight; // 体重只有自己知道
   	
    public SmallPineapple() {}

    private SmallPineapple(String name){
        this.name = name;
    }
    
    public SmallPineapple(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void show(){
        System.out.println("test");
    }

    private void showString(String msg){
        System.out.println(msg);
    }
    public void getInfo() {
        System.out.print("["+ name + " 的年龄是：" + age + "]");
    }
}