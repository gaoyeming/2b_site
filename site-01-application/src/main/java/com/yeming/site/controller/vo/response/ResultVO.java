package com.yeming.site.controller.vo.response;

import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author yeming.gao
 * @Description: 响应结果
 * @date 2019/11/7 13:52
 */

@ApiModel(description = "响应返回对象")
@Data
public class ResultVO<T> {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_SERVER_ERROR = 500;

    /**
     * 返回结果代码
     */
    @ApiModelProperty(value = "返回信息代码", name = "code", example = "000")
    private String code;
    /**
     * 具体的错误信息
     */
    @ApiModelProperty(value = "返回信息描述",  name = "message", example = "交易成功",position = 1)
    private String message;
    /**
     * 返回结果数据
     */
    @ApiModelProperty(value = "返回结果数据", name = "data", position = 2)
    private T data;

    public void returnSuccessWithData(T data) {
        this.code = RespCodeEnum.SUCCESS.getCode();
        this.message = RespCodeEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public void returnSuccess() {
        this.code = RespCodeEnum.SUCCESS.getCode();
        this.message = RespCodeEnum.SUCCESS.getMessage();
    }

    public void returnSystemError() {
        this.code = RespCodeEnum.SYS_FAIL.getCode();
        this.message = RespCodeEnum.SYS_FAIL.getMessage();
    }

    public void returnException(SiteException re) {
        this.code = re.getCode();
        this.message = re.getMessage();
    }

    public void genFailResult(String message) {
        setResp(RespCodeEnum.SERVER_FAIL);
        if (!StringUtils.isEmpty(message)) {
            this.message = message;
        }
    }

    private void setResp(RespCodeEnum respCodeEnum) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getMessage();
    }

    public void returnRequestFail(String result) {
        setResp(RespCodeEnum.REQUEST_FAIL);
        this.message = result;
    }

}
