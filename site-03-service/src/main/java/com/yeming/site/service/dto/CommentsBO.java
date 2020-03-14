package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 评论传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class CommentsBO extends BaseBO {

    private Integer commentId;
    private String commentBody;
    private String commentatorIp;
    private String commentCreateTime;
    private String commentator;
    private String email;
    private Integer commentStatus;
    private String replyBody;
    private String replyCreateTime;
    private Long blogId;
    private String verifyCode;
    private String websiteUrl;

    /**
     * 评论查询结果集合
     */
    private List<CommentsBO> resultList = new ArrayList<>();
}
