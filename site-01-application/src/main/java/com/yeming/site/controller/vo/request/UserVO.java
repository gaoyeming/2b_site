package com.yeming.site.controller.vo.request;

import com.yeming.site.aop.annotation.EnumValidator;
import com.yeming.site.util.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Base64;

/**
 * @author yeming.gao
 * @Description: 登陆用户请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "userVO对象", description = "登陆用户操作对象")
@Setter
@Getter
public class UserVO extends CommonVO {

    /**
     * 网站相关配置
     */
    @ApiModelProperty(value = "登录名", name = "loginUserName", example = "admin")
    @Length(max = 50, message = "登录名过长")
    private String loginUserName;

    @ApiModelProperty(value = "昵称", name = "nickName", example = "管理员")
    @Length(max = 50, message = "昵称过长")
    private String nickName;

    @ApiModelProperty(value = "旧密码", name = "originalPassword", example = "123456")
    @Length(max = 50, message = "旧密码过长")
    private String originalPassword;

    @ApiModelProperty(value = "新密码", name = "newPassword", example = "654321")
    @Length(max = 50, message = "新密码过长")
    private String newPassword;

    @ApiModelProperty(value = "操作类型", name = "opterType", example = "操作类型选值范围:0-修改名称/1-修改密码")
    @NotNull(message = "请输入操作类型")
    @EnumValidator(enumClass = StatusEnum.class, message = "操作类型选值范围:0-修改名称/1-修改密码")
    private Integer opterType;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("loginUserName", getLoginUserName())
                .add("nickName", getNickName())
                .add("originalPassword", Base64.getEncoder().encode(getOriginalPassword().getBytes()))
                .add("newPassword", Base64.getEncoder().encode(getNewPassword().getBytes()))
                .add("opterType", getOpterType()).toString();
    }

}
