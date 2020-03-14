package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yeming.gao
 * @Description: 登陆用户传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class UserBO extends BaseBO {

    private String loginUserName;
    private String nickName;
    private String originalPassword;
    private String newPassword;
    private Integer opterType;
}
