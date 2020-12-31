package com.ling.other.modules.webSocket.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 标注一个主程序，说明这是一个springboot应用
 */
@SpringBootApplication
public class SpringBootMainApplication {

    //编写主程序方法
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainApplication.class);

        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyThread myThread = new MyThread();
            Thread thread = new Thread(myThread);
            thread.start();
        }

    }
}