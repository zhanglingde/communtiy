package com.ling.other.modules.webSocket.service;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @Component 将类注入到容器
 * @ServerEndpoint 前端通过这个url进行访问通信 建立连接
 */
@ServerEndpoint("/websocket")
@Component
public class MyWebSocket {

    //存放websocket 的线程安全的无序的集合
    private static CopyOnWriteArraySet<MyWebSocket> websocket = new CopyOnWriteArraySet<MyWebSocket>();

    private Session session;

    public static CopyOnWriteArraySet<MyWebSocket> getWebsocket() {
        return websocket;
    }

    public static void setWebsocket(CopyOnWriteArraySet<MyWebSocket> websocket) {
        MyWebSocket.websocket = websocket;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocket.add(this);     //加入set中
        // addOnlineCount();           //在线数加1
        System.out.println("进入onOpen方法");
        try {
            sendMessage("连接已建立成功.");
        } catch (Exception e) {
            System.out.println("IO异常");
        }
    }


    /**
     * 关闭通信连接
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        //关闭连接后将此socket删除
        websocket.remove(this);
        System.out.println("进入onClose方法");
    }

    /**
     * 获取客户端发来的信息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("进入onMessage方法; message = " + message);
    }

    /**
     * 给客户端推送信息
     */
    public void sendMessage(String message) throws IOException {
        System.out.println("进入sendMessage方法");
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 异常方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("进入error方法");
        error.printStackTrace();
    }

}