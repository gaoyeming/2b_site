package com.yeming.site.biz;

import com.yeming.site.service.dto.SendTextMessageBO;
import com.yeming.site.util.FileUtils;
import com.yeming.site.util.PropertiesUtils;
import com.yeming.site.util.XmlUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.enums.WeChatMsgTypeEnum;
import com.yeming.site.util.exception.SiteException;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 微信处理服务层
 * @date 2019/6/27 20:40
 */
@Service
public class WeChatBiz {
    private static Logger LOGGER = LoggerFactory.getLogger(WeChatBiz.class);

    @Resource
    private PropertiesUtils propertiesUtils;

    /**
     * 获取微信端发来的消息
     *
     * @param request request请求
     * @return Map<String   ,       String>
     * @throws IOException,DocumentException 异常
     */
    public Map<String, String> getMsg(HttpServletRequest request) throws IOException, DocumentException {
        //获取微信端发来的信息
        InputStream inputStream = request.getInputStream();
        String xml = inputStreamToString(inputStream);
        Map<String, String> receiveMsg = XmlUtils.xmlToMap(xml);

        String fromUserName = receiveMsg.get("FromUserName");
        String msgType = receiveMsg.get("MsgType");
        if (WeChatMsgTypeEnum.EVENT.getCode().equals(msgType)) {
            String event = receiveMsg.get("Event");
            if (WeChatMsgTypeEnum.SUBSCRIBE.getCode().equals(event)) {
                LOGGER.info("微信端用户：{}关注了我的个人公众号", fromUserName);
            } else if (WeChatMsgTypeEnum.UNSUBSCRIBE.getCode().equals(event)) {
                LOGGER.info("微信端用户：{}取消关注了我的个人公众号", fromUserName);
            } else {
                LOGGER.info("微信端用户：{}操作了未知类型：{}", fromUserName, event);
            }
        } else if (WeChatMsgTypeEnum.TEXT.getCode().equals(msgType)) {
            String content = receiveMsg.get("Content");
            LOGGER.info("接收到微信端用户：{}发来的:{}", fromUserName, content);
        } else {
            LOGGER.info("来自微信端用户：{}暂时不支持的消息类型：{}", fromUserName,
                    WeChatMsgTypeEnum.value(msgType) == null ? "未知" : Objects.requireNonNull(WeChatMsgTypeEnum.value(msgType)).getDesc());
        }
        return receiveMsg;
    }

    public void sendMsg(Map<String, String> receiveMsg, HttpServletResponse response) throws IOException {
        //返回对应消息
        String fromUserName = receiveMsg.get("FromUserName");
        String toUserName = receiveMsg.get("ToUserName");
        //自动回复
        SendTextMessageBO text = new SendTextMessageBO();
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setCreateTime(String.valueOf(System.currentTimeMillis()));
        text.setMsgType(WeChatMsgTypeEnum.TEXT.getCode());

        //获取消息类型
        String msgType = receiveMsg.get("MsgType");
        if (WeChatMsgTypeEnum.EVENT.getCode().equals(msgType)) {
            String event = receiveMsg.get("Event");
            if (WeChatMsgTypeEnum.SUBSCRIBE.getCode().equals(event)) {
                //被关注发送的消息
                String sendContent = FileUtils.getFileContent(propertiesUtils.getWechatReplyFilePath());
                text.setContent(sendContent);
            } else {
                //取消关注（以及其他操作）则不做任何动作
                throw new SiteException(RespCodeEnum.WEIXIN_UNSUBSCRIBE);
            }
        } else if (WeChatMsgTypeEnum.TEXT.getCode().equals(msgType)) {
            //获取消息
            String content = receiveMsg.get("Content");
            if(content.contains("留言")){
                text.setContent("感谢你的留言!!!");
            }else if(content.contains("文章")){
                text.setContent("返回对应搜索文章!!!");
            }else{
                text.setContent("返回历史文章!!!");
            }

        } else {
            text.setContent(WeChatMsgTypeEnum.value(msgType) == null ? "sorry,,,消息未知类型无法识别" :
                    "sorry,,," + Objects.requireNonNull(WeChatMsgTypeEnum.value(msgType)).getDesc() + "暂不支持");
        }

        String xml = XmlUtils.objectToXml(text, AllConstants.Common.ROOT_XML);
        //将xml转换成首字母大写
        String sendMsg = XmlUtils.xmlTagCapitalize(xml, AllConstants.Common.ROOT_XML);
        try {
            response.getOutputStream().write(sendMsg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.warn("回复微信端发来的信息出现异常：{}", e.getMessage());
            throw new SiteException(RespCodeEnum.WEIXIN_SEND_MSG_FAIL);
        }
    }

    /**
     * 将 inputStream 转String
     *
     * @param inputStream 输入流
     * @return String
     * @throws IOException IO异常
     */
    private String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
