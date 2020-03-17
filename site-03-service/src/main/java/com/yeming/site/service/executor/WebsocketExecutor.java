package com.yeming.site.service.executor;

import com.yeming.site.service.WebSocketService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yeming.gao
 * @Description: websocket异步线程
 * @date 2020/3/17 10:20
 */
@Service
public class WebsocketExecutor {

    @Resource
    private WebSocketService webSocketService;

    @Async("asyncServiceExecutor")
    public void sendInfo(String conent){
        webSocketService.sendInfo(conent);
    }
}
