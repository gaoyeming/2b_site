package com.yeming.site.service.dto;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yeming.gao
 * @Description: 返回内容字段
 * @date 2019/6/27 21:04
 */
@Setter
@Getter
public class SendTextMessageBO {

    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String content;
    private String msgId;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("toUserName", toUserName)
                .add("fromUserName", fromUserName)
                .add("createTime", createTime)
                .add("msgType", msgType)
                .add("content", content)
                .add("msgId", msgId).toString();
    }
}
