package com.yeming.site.util.enums;

/**
 * @author yeming.gao
 * @Description: 微信消息类型
 * @date 2019/6/11 9:47
 */
public enum WeChatMsgTypeEnum {
    /**
     * 事件类型（目前包括subscribe-被关注；unsubscribe-取消关注）
     */
    EVENT("event", "事件类型"),
    /**
     * 被关注
     */
    SUBSCRIBE("subscribe", "被关注"),
    /**
     * 取消关注
     */
    UNSUBSCRIBE("unsubscribe", "取消关注"),
    /**
     * 文本消息
     */
    TEXT("text", "文本消息"),
    /**
     * 图片消息
     */
    IMAGE("image", "图片消息"),
    /**
     * 语音消息
     */
    VOICE("voice", "语音消息"),
    /**
     * 视频消息
     */
    VIDEO("video", "视频消息"),
    /**
     * 小视频消息
     */
    SHORTVIDEO("shortvideo", "小视频消息"),
    /**
     * 地理位置消息
     */
    LOCATION("location", "地理位置消息"),
    /**
     * 链接消息
     */
    LINK("link", "链接消息");

    private String code;
    private String desc;

    WeChatMsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WeChatMsgTypeEnum value(String code){
        for(WeChatMsgTypeEnum weChatMsgTypeEnum : WeChatMsgTypeEnum.values()) {
            if(weChatMsgTypeEnum.code.equals(code)) {
                return weChatMsgTypeEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
