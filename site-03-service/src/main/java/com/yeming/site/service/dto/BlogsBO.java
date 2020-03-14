package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 博客传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class BlogsBO extends BaseBO {

    private Integer blogId;
    private String blogTitle;
    private String blogSubUrl;
    private String blogCoverImage;
    private String blogContent;
    private Integer blogCategoryId;
    private String blogCategoryIcon;
    private String blogCategoryName;
    private String blogTags;
    private Integer blogTagId;
    private List<TagsBO> blogTagsDesc;
    private Integer blogViews;
    private Integer blogStatus;
    private String blogStatusDesc;
    private Integer enableComment;
    private String addTime;
    private String formatDate;
    private Integer commentCount;
    private Integer selectTpye;

    /**
     * 博客查询结果集合
     */
    private List<BlogsBO> resultList = new ArrayList<>();
}
