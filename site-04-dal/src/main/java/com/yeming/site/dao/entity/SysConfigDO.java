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
 * @Description: 系统配置表
 * @date 2019/11/26 15:38
 */
@Table(name = "sys_config")
@Setter
@Getter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class SysConfigDO extends BaseDO implements Serializable {

    /**
     * 配置项名称
     **/
    @Column
    private String configName;
    /**
     * 配置项值
     **/
    @Column
    private String configValue;
    /**
     * 配置项说明
     **/
    @Column(updatable = false)
    private String configDesc;
    /**
     * 是否删除
     **/
    @Column(insertable = false,updatable = false)
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("configName", getConfigName())
                .add("configValue", getConfigValue())
                .add("configDesc", getConfigDesc())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
