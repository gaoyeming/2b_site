package com.yeming.site.dao.entity;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yeming.gao
 * @Description: 基础实体类
 * @date 2019/11/4 21:11
 */
@Setter
@Getter
@MappedSuperclass
public class BaseDO implements Serializable {
    /**用户数据库主键**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 创建时间
     **/
    @Column(insertable = false,updatable = false)
    private Date createTime;
    /**
     * 更新时间
     **/
    @Column(insertable = false,updatable = false)
    private Date updateTime;

    MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("createTime", getCreateTime())
                .add("updateTime", getUpdateTime());
    }
}
