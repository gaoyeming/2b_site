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
 * @Description: 博客友链表
 * @date 2019/11/26 15:38
 */
@Table(name = "backstage_link")
@Setter
@Getter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class BackstageLinkDO extends BaseDO implements Serializable {

    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     **/
    @Column
    private Integer linkType;
    /**
     * 网站名称
     **/
    @Column
    private String linkName;
    /**
     * 网站链接
     **/
    @Column
    private String linkUrl;
    /**
     * 网站描述
     **/
    @Column
    private String linkDescription;
    /**
     * 用于列表排序
     **/
    @Column
    private Integer linkRank;
    /**
     * 是否删除
     **/
    @Column(insertable = false,updatable = false)
    private Integer isDeleted;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("linkType", getLinkType())
                .add("linkName", getLinkName())
                .add("linkUrl", getLinkUrl())
                .add("linkDescription", getLinkDescription())
                .add("linkRank", getLinkRank())
                .add("isDeleted", getIsDeleted()).toString();
    }
}
