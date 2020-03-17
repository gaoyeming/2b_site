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
 * @Description: 系统操作日志表
 * @date 2019/11/26 15:38
 */
@Table(name = "sys_operation_log")
@Setter
@Getter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class SysOperationLogDO extends BaseDO implements Serializable {

    /**
     * 登陆用户
     **/
    @Column
    private String operUser;
    /**
     * 操作IP
     **/
    @Column
    private String operIp;
    /**
     * 操作url
     **/
    @Column
    private String operUrl;
    /**
     * 操作标题
     **/
    @Column
    private String operTitle;
    /**
     * 操作参数
     **/
    @Column
    private String operParams;
    /**
     * 操作返回结果
     **/
    @Column
    private String operResult;
    /**
     * 操作方法
     **/
    @Column
    private String operMethod;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("operUser", getOperUser())
                .add("operIp", getOperIp())
                .add("operUrl", getOperUrl())
                .add("operTitle", getOperTitle())
                .add("operParams", getOperParams())
                .add("operResult", getOperResult())
                .add("operMethod", getOperMethod()).toString();
    }
}
