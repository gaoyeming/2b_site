package com.yeming.site.util.enums;

/**
 * @author yeming.gao
 * @Description: 友链类型
 * @date 2019/12/5 17:48
 */
public enum LinkTypeEnum {
    /**
     * 0-友链
     */
    FRIEND_LINK(0, "友链"),
    /**
     * 1-推荐
     */
    RECOMMEND(1, "推荐"),
    /**
     * 2-个人网站
     */
    PERSONAL_SITE(2, "个人网站"),
    ;
    private Integer code;
    private String desc;

    LinkTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
