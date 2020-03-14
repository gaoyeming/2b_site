package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yeming.gao
 * @Description: 博客分类表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_category")
@Setter
@Getter
@Entity
public class BackstageCategoryDO extends BaseDO implements Serializable {

    /**
     * 分类名称
     **/
    @Column
    private String categoryName;
    /**
     * 分类图标
     **/
    @Column
    private String categoryIcon;
    /**
     * 分类等级
     **/
    @Column(insertable = false, updatable = false)
    private Integer categoryRank;
    /**
     * 是否删除
     **/
    @Column(insertable = false, updatable = false)
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("categoryName", getCategoryName())
                .add("categoryIcon", getCategoryIcon())
                .add("categoryRank", getCategoryRank())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
