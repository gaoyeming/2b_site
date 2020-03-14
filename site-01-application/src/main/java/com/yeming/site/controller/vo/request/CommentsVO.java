package com.yeming.site.controller.vo.request;

import com.yeming.site.service.dto.CommentsBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 评论请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "commentsVO对象", description = "评论操作对象")
@Setter
@Getter
public class CommentsVO extends CommonVO {

    @ApiModelProperty(value = "博客id", name = "blogId", example = "1")
    @NotNull(message = "请输入博客id")
    private Long blogId;
    @ApiModelProperty(value = "验证码", name = "verifyCode", example = "123as")
    @NotEmpty(message = "请输入验证码")
    @Length(min = 5, max = 5, message = "验证码长度不正确")
    private String verifyCode;
    @ApiModelProperty(value = "评论者名称", name = "commentator", example = "张三")
    @NotEmpty(message = "请输入评论者名称")
    @Length(max = 50, message = "评论者名称太长")
    private String commentator;
    @ApiModelProperty(value = "评论人的邮箱", name = "email", example = "yeming.gao@aliyun.com")
    @NotEmpty(message = "请输入评论人的邮箱")
    @Length(max = 100, message = "评论人的邮箱太长")
    @Email(message = "邮箱格式不符合要求")
    private String email;
    @ApiModelProperty(value = "网址", name = "verifyCode", example = "http://site.liufang.xn--6qq986b3xl/")
    @Length(max = 100, message = "网址太长")
    private String websiteUrl;
    @ApiModelProperty(value = "评论内容", name = "commentBody", example = "哈哈哈")
    @NotEmpty(message = "请输入评论内容")
    @Length(max = 200, message = "评论内容太长")
    private String commentBody;
    /**
     * 评论查询结果集合
     */
    private List<CommentsBO> resultList;
}
