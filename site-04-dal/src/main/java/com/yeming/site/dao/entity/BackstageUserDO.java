package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yeming.gao
 * @Description: 后台管理系统登陆用户信息表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_user")
@Setter
@Getter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class BackstageUserDO extends BaseDO implements Serializable {

    /**
     * 登陆用户
     **/
    @Column
    private String loginUser;
    /**
     * 登陆用户密码
     **/
    @Column
    private String loginPassword;
    /**
     * 登陆用户别名
     **/
    @Column
    private String nickName;
    /**
     * 账号是否锁定
     **/
    @Column(insertable = false,updatable = false)
    private Integer locked;
    /**
     * 是否删除
     **/
    @Column(insertable = false)
    private Integer isDeleted;


    @Override
    public String toString() {
        return super.toStringHelper()
                .add("loginUser", getLoginUser())
                .add("loginPassword", getLoginPassword())
                .add("nickName", getNickName())
                .add("locked", getLocked())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
