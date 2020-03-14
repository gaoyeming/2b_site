package com.yeming.site.util.enums;


/**
 * @author yeming.gao
 * @Description: 系统共用的枚举
 * @date 2019/12/5 17:48
 */
public enum RespCodeEnum implements IRespCode {
    /**
     * 交易成功
     */
    SUCCESS("000", "交易成功"),
    /**
     * 系统异常
     */
    SYS_FAIL("999", "系统异常"),
    /**
     * 服务器处理失败
     */
    REQUEST_FAIL("400", "参数校验失败"),
    /**
     * 服务器处理失败
     */
    SERVER_FAIL("500", "服务器处理失败"),
    /**
     * 无数据
     */
    NO_DATA("001", "无数据"),
    /**
     * 弹幕发送失败
     */
    BARRAGE_SEND_FAIL("002", "弹幕发送失败"),
    /**
     * 未知的操作类型
     */
    UNKNOWN_OPERATION_TYPE("003", "未知的操作类型"),
    ;
    private String code;
    private String message;

    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
