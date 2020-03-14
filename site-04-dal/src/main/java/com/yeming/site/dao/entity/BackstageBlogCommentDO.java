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
 * @Description: 博客评论表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_blog_comment")
@Setter
@Getter
@Entity
public class BackstageBlogCommentDO extends BaseDO implements Serializable {

    /**
     * 关联博客主键
     **/
    @Column
    private Integer blogId;
    /**
     * 评论者名称
     **/
    @Column
    private String commentator;
    /**
     * 评论人的邮箱
     **/
    @Column
    private String email;
    /**
     * 网址
     **/
    @Column
    private String websiteUrl;
    /**
     * 评论内容
     **/
    @Column
    private String commentBody;
    /**
     * 评论时的ip地址
     **/
    @Column
    private String commentatorIp;
    /**
     * 是否回复 0-未回复 1-已回复
     **/
    @Column(insertable = false)
    private Integer isReplyed;
    /**
     * 回复内容
     **/
    @Column(insertable = false)
    private String replyBody;
    /**
     * 回复时间
     **/
    @Column(insertable = false)
    private Date replyTime;
    /**
     * 是否审核通过 0-未审核 1-审核通过
     **/
    @Column
    private Integer commentStatus;
    /**
     * 是否删除
     **/
    @Column
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("blogId", getBlogId())
                .add("commentator", getCommentator())
                .add("email", getEmail())
                .add("websiteUrl", getWebsiteUrl())
                .add("commentBody", getCommentBody())
                .add("commentatorIp", getCommentatorIp())
                .add("isReplyed", getIsReplyed())
                .add("replyBody", getReplyBody())
                .add("replyTime", getReplyTime())
                .add("commentStatus", getCommentStatus())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
