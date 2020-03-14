package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yeming.gao
 * @Description: 博客标签关联表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_blog_tag_relation")
@Setter
@Getter
@Entity
public class BackstageBlogTagRelationDO extends BaseDO implements Serializable {

    /**
     * 博客标题
     **/
    @Column
    private Integer blogId;
    /**
     * 博客自定义路径url
     **/
    @Column
    private Integer tagId;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("blogId", getBlogId())
                .add("tagId", getTagId()).toString();
    }
}
