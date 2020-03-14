package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yeming.gao
 * @Description: 博客表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_blog")
@Setter
@Getter
@Entity
public class BackstageBlogDO extends BaseDO implements Serializable {

    public BackstageBlogDO() {
    }

    /**
     * 对应jpa自定义的sql查询BackstageBlogRepository
     */
    public BackstageBlogDO(Long id, String blogTitle, String blogSubUrl, String blogCoverImage,
                           String blogContent, Integer blogCategoryId, Integer blogStatus,
                           Integer blogViews, Integer enableComment, Date createTime) {
        super.setId(id);
        this.blogTitle = blogTitle;
        this.blogSubUrl = blogSubUrl;
        this.blogCoverImage = blogCoverImage;
        this.blogContent = blogContent;
        this.blogCategoryId = blogCategoryId;
        this.blogStatus = blogStatus;
        this.blogViews = blogViews;
        this.enableComment = enableComment;
        super.setCreateTime(createTime);
    }

    /**
     * 博客标题
     **/
    @Column
    private String blogTitle;
    /**
     * 博客自定义路径url
     **/
    @Column
    private String blogSubUrl;
    /**
     * 博客封面图
     **/
    @Column
    private String blogCoverImage;
    /**
     * 博客内容
     **/
    @Column
    private String blogContent;
    /**
     * 博客分类id
     **/
    @Column
    private Integer blogCategoryId;
    /**
     * 0-草稿 1-发布
     **/
    @Column
    private Integer blogStatus;
    /**
     * 阅读量
     **/
    @Column(insertable = false, updatable = false)
    private Integer blogViews;
    /**
     * 0-允许评论 1-不允许评论
     **/
    @Column
    private Integer enableComment;
    /**
     * 是否删除
     **/
    @Column
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("blogTitle", getBlogTitle())
                .add("blogSubUrl", getBlogSubUrl())
                .add("blogCoverImage", getBlogCoverImage())
                .add("blogContent", getBlogContent())
                .add("blogCategoryId", getBlogCategoryId())
                .add("blogStatus", getBlogStatus())
                .add("blogViews", getBlogViews())
                .add("enableComment", getEnableComment())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
