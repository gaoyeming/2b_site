package com.yeming.site.util.enums;

/**
 * @author yeming.gao
 * @Description: 所有0/1的状态值
 * @date 2019/12/5 17:48
 */
public enum StatusEnum {
    /**
     * 1
     */
    ONE(1, "1"),
    /**
     * 0
     */
    ZERO(0, "0"),
    ;
    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 判断参数合法性
     */
    public static StatusEnum findByCode(Integer code) {
        for (StatusEnum deletedEnum : StatusEnum.values()) {
            if (deletedEnum.getCode().equals(code)) {
                return deletedEnum;
            }
        }
        return null;
    }
}
