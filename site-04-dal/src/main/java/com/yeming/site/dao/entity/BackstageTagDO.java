package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yeming.gao
 * @Description: 博客标签表表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_tag")
@Setter
@Getter
@Entity
public class BackstageTagDO extends BaseDO implements Serializable {

    /**
     * 标签名称
     **/
    @Column
    private String tagName;
    /**
     * 是否删除
     **/
    @Column(insertable = false)
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("tagName", getTagName())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
