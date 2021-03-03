package com.ling.other.modules.webSocket.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.webSocket.service.MyWebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zhangling 2021/1/4 13:50
 */
@RestController
@RequestMapping("/websocket")
@Api(value = "Socket通信",tags = "Socket通信")
public class SocketController {

    CopyOnWriteArraySet<MyWebSocket> websocket = MyWebSocket.getWebsocket();

    @Autowired
    MyWebSocket myWebSocket;

    @ApiOperation("发送消息给客户端")
    @GetMapping("/sendMessage")
    public CommonResult sendMessage(String message,Integer companyId) throws IOException {
        for (MyWebSocket myWebSocket : websocket) {
            if(myWebSocket.getCompanyId().equals(companyId)){
                try {
                    myWebSocket.sendMessage(message);
                    System.out.println("发送消息给客户端："+message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return CommonResult.success();
    }
}
