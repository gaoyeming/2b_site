package com.yeming.site.util.exception;

import com.yeming.site.util.enums.IRespCode;
import com.yeming.site.util.enums.RespCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yeming.gao
 * @Description: 自定义异常
 * @date 2019/12/5 17:40
 */
@Setter
@Getter
public class SiteException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 0x62980f30d0d36e8aL;
    /**
     * 错误码
     */
    private String code;
    /**
     * 标准错误信息
     */
    private String message;


    public SiteException(IRespCode iRespCode) {
        this.code = iRespCode.getCode();
        this.message = iRespCode.getMessage();
    }

    public SiteException(String message) {
        this.code = RespCodeEnum.SERVER_FAIL.getCode();
        this.message = message;
    }

}