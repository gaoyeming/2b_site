package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageUserDO;
import com.yeming.site.dao.repository.BackstageUserRepository;
import com.yeming.site.service.dto.UserBO;
import com.yeming.site.util.CipherUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.enums.StatusEnum;
import com.yeming.site.util.exception.SiteException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author yeming.gao
 * @Description: 后台管理登录用户表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageUserService {

    @Resource
    private BackstageUserRepository backstageUserRepository;


    public BackstageUserDO findOne(String loginUser) {
        BackstageUserDO userDO = new BackstageUserDO();
        userDO.setLoginUser(loginUser);
        Example<BackstageUserDO> example = Example.of(userDO);
        Optional<BackstageUserDO> exampleResult = backstageUserRepository.findOne(example);
        return exampleResult.orElse(null);
    }

    /**
     * 更新用户信息
     * @param userBO 需要更新的参数信息
     * @param loginUser 当前登陆的用户
     * @throws Exception 密码加密异常
     */
    public void updateUser(UserBO userBO, BackstageUserDO loginUser) throws Exception {
        if (Objects.isNull(StatusEnum.findByCode(userBO.getOpterType()))) {
            throw new SiteException(RespCodeEnum.UNKNOWN_OPERATION_TYPE);
        }
        //修改名称
        if (StatusEnum.ZERO.getCode().equals(userBO.getOpterType())) {
            if (StringUtils.isEmpty(userBO.getLoginUserName()) ||
                    StringUtils.isEmpty(userBO.getNickName())) {
                throw new SiteException("修改名称:登陆名称或昵称不能为空");
            }
            loginUser.setLoginUser(userBO.getLoginUserName());
            loginUser.setNickName(userBO.getNickName());
            backstageUserRepository.save(loginUser);

        }
        //修改密码
        if (StatusEnum.ONE.getCode().equals(userBO.getOpterType())) {
            if (StringUtils.isEmpty(userBO.getOriginalPassword()) ||
                    StringUtils.isEmpty(userBO.getNewPassword())) {
                throw new SiteException("修改名称:登陆名称或昵称不能为空");
            }
            //获取旧密码於数据库密码进行比对
            String password = CipherUtils.enAes(userBO.getOriginalPassword(), AllConstants.Web.AES_SALT);
            if (!password.equals(loginUser.getLoginPassword())) {
                throw new SiteException("旧密码不正确");
            }
            String newPassword = CipherUtils.enAes(userBO.getNewPassword(), AllConstants.Web.AES_SALT);
            loginUser.setLoginPassword(newPassword);
            backstageUserRepository.save(loginUser);
        }
    }
}
