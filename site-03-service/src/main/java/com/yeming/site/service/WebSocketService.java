package com.yeming.site.service;

import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yeming.gao
 * @Description: WebSocket服务类
 * @date 2019/6/3 17:51
 */
@Service
@ServerEndpoint("/yeming.gao/websocket")
public class WebSocketService {
    private static Logger LOGGER = LoggerFactory.getLogger(WebSocketService.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * 用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final CopyOnWriteArraySet<WebSocketService> WEB_SOCKET_SET =
            new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入set中
        WEB_SOCKET_SET.add(this);
        addOnlineCount(); //在线数加1
        LOGGER.info("当前开启的弹幕个数为：" + getOnlineCount());
        try {
            sendMessage("WebSocket连接成功");
        } catch (IOException e) {
            LOGGER.error("websocket IO异常：{}", e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        this.session = session;
        //从set中删除
        WEB_SOCKET_SET.remove(this);
        subOnlineCount(); //在线数减1
        LOGGER.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        this.session = session;
        LOGGER.info("收到信息:" + message);
        //群发消息
        for (WebSocketService item : WEB_SOCKET_SET) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session session对象
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        this.session = session;
        LOGGER.error("发生错误:{}", error.getMessage());
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public void sendInfo(String message) {
        WEB_SOCKET_SET.forEach(item -> {
            try {
                item.sendMessage(message);
                LOGGER.info("发送弹幕消息:" + message);
            } catch (IOException e) {
                LOGGER.error("发送弹幕消息出现异常：{}", e.getMessage());
                throw new SiteException(RespCodeEnum.BARRAGE_SEND_FAIL);
            }
        });
    }

    private synchronized int getOnlineCount() {
        return onlineCount;
    }

    private synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    private synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
