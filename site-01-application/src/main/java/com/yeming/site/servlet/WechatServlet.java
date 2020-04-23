package com.yeming.site.servlet;

import com.yeming.site.biz.WeChatBiz;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.IPUtils;
import com.yeming.site.util.PropertiesUtils;
import com.yeming.site.util.WechatCheckSignUtils;
import com.yeming.site.util.exception.SiteException;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 微信服务端servlet
 * @date 2020/4/22 16:34
 */
@WebServlet(name = "wechatServlet", urlPatterns = "/wechat.do")
public class WechatServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatServlet.class);

    private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private PropertiesUtils propertiesUtils;

    @Resource
    private WeChatBiz weChatBiz;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOGGER.info("{}启用微信订阅号服务器配置", DateUtils.getCurrentDateToStr(FORMAT_DATE));
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (WechatCheckSignUtils.checkSignature(signature, timestamp, nonce, propertiesUtils.getWechatToken())) {
            // 校验通过，原样返回echostr参数内容
            out.print(echostr);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("{}获取ip={}的微信订阅号请求", DateUtils.getCurrentDateToStr(FORMAT_DATE), IPUtils.getIpAddr(request));
        try {
            Map<String, String> msg = weChatBiz.getMsg(request);
            //回复消息
            weChatBiz.sendMsg(msg, response);
        } catch (SiteException e) {
            LOGGER.error("微信端信息处理失败:", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("微信端信息处理出现异常:", e);
        }
    }
}
