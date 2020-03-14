package com.yeming.site.util.enums;

/**
 * @author yeming.gao
 * @Description: 是否删除 0-未删除 1-已删除
 * @date 2019/12/5 17:48
 */
public enum DeletedEnum {
    /**
     * 0-未删除
     */
    NO_DELETED(0, "未删除"),
    /**
     * 1-已删除
     */
    IS_DELETED(1, "已删除"),
    ;
    private Integer code;
    private String desc;

    DeletedEnum(Integer code, String desc) {
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
