package com.ling.other.modules.webSocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
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

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    //存放websocket 的线程安全的无序的集合
    private static CopyOnWriteArraySet<MyWebSocket> websocket = new CopyOnWriteArraySet<MyWebSocket>();

    private Integer companyId;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        String companyId = session.getQueryString().split("=")[1];
        this.companyId = Integer.valueOf(companyId);

        websocket.add(this);     //加入set中
        // addOnlineCount();           //在线数加1

        logger.info("进入onOpen方法");
        try {
            sendMessage("连接已建立成功.");
        } catch (Exception e) {
            logger.error("IO异常");
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
        logger.info("关闭socket连接");
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
        logger.info("进入sendMessage方法");
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 异常方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("进入error方法");
        error.printStackTrace();
    }

}