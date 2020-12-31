package com.ling.other.modules.webSocket.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

public class MyThread implements Runnable {

    @Override
    public void run() {

        CopyOnWriteArraySet<MyWebSocket> websocket = MyWebSocket.getWebsocket();
        for (MyWebSocket myWebSocket : websocket) {
            try {
                double random = Math.random();
                myWebSocket.sendMessage("我要发消息了" + random);
                System.out.println("发送消息给客户端："+random);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}