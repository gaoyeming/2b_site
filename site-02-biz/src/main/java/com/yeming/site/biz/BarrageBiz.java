package com.yeming.site.biz;

import com.yeming.site.service.WebSocketService;
import com.yeming.site.util.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yeming.gao
 * @Description: 弹幕服务层
 * @date 2019/6/13 14:41
 */
@Component
public class BarrageBiz {
    private static Logger LOGGER = LoggerFactory.getLogger(BarrageBiz.class);

    @Resource
    private WebSocketService webSocketService;
    @Resource
    private HttpServletRequest request;


    public void sendInfo(String content) {
        //首先获取IP
        String ipAdress = IPUtils.getIpAddr(request);
        LOGGER.info("收到IP={},发送的弹幕消息：{}", ipAdress, content);
        //发送弹幕消息
        webSocketService.sendInfo(content);
    }

}
