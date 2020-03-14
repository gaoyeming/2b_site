package com.yeming.site.util.enums;

/**
 * @author yeming.gao
 * @Description: 前端页面查询类型
 * @date 2019/12/5 17:48
 */
public enum SelectTypeEnum {
    /**
     * 0-关键字查询
     */
    KEY_WORD(0, "关键字查询"),
    /**
     * 1-分类查询
     */
    CATEGORY(1, "分类查询"),
    /**
     * 2-标签查询
     */
    TAG(2, "标签查询"),
    ;
    private Integer code;
    private String desc;

    SelectTypeEnum(Integer code, String desc) {
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
