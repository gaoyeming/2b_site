package com.yeming.site.controller.vo.request;

import com.yeming.site.aop.annotation.EnumValidator;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.util.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 博客请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "blogsVO对象", description = "博客操作对象")
@Setter
@Getter
public class BlogsVO extends CommonVO {

    /**
     * 博客标题
     **/
    @ApiModelProperty(value = "文章标题", name = "blogTitle", example = "Java基础")
    @NotEmpty(message = "请输入文章标题")
    @Length(max = 200, message = "文章标题过长")
    private String blogTitle;
    /**
     * 博客自定义路径url
     **/
    @ApiModelProperty(value = "自定义路径url", name = "blogSubUrl", example = "java doc")
    @Length(max = 200, message = "自定义路径url过长")
    private String blogSubUrl;
    /**
     * 博客封面图
     **/
    @ApiModelProperty(value = "博客封面图", name = "blogCoverImage", example = "images/a.png")
    @NotEmpty(message = "封面图不能为空")
    @Length(max = 200, message = "封面图路径过长")
    private String blogCoverImage;
    /**
     * 博客内容
     **/
    @ApiModelProperty(value = "文章内容", name = "blogContent", example = "XXXXXXX......")
    @NotEmpty(message = "请输入文章内容")
    @Length(min = 1, message = "文章内容太少啦")
    private String blogContent;
    /**
     * 博客分类id
     **/
    @ApiModelProperty(value = "博客分类id", name = "blogCategoryId", example = "0")
    @NotNull(message = "请输入博客分类id")
    private Integer blogCategoryId;
    /**
     * 博客标签
     **/
    @ApiModelProperty(value = "文章标签", name = "blogTags", example = "基础,升级,理论")
    @NotEmpty(message = "请输入文章标签")
    @Length(max = 200, message = "文章标签过长")
    private String blogTags;
    /**
     * 0-草稿 1-发布
     **/
    @ApiModelProperty(value = "是否发布", name = "blogStatus", example = "博客状态选值:0-草稿/1-发布")
    @NotNull(message = "请输入博客状态")
    @EnumValidator(enumClass = StatusEnum.class, message = "博客状态选值范围:0-草稿/1-发布")
    private Integer blogStatus;
    /**
     * 0-允许评论 1-不允许评论
     **/
    @ApiModelProperty(value = "是否允许评论", name = "enableComment", example = "是否允许评论选值:0-允许评论/1-不允许评论")
    @NotNull(message = "请输入博客是否允许评论")
    @EnumValidator(enumClass = StatusEnum.class, message = "是否允许评论选值范围:0-允许评论/1-不允许评论")
    private Integer enableComment;

    /**
     * 博客查询结果集合
     */
    private List<BlogsBO> resultList;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("blogTitle", getBlogTitle())
                .add("blogSubUrl", getBlogSubUrl())
                .add("blogCoverImage", getBlogCoverImage())
                .add("blogContent", getBlogContent())
                .add("blogCategoryId", getBlogCategoryId())
                .add("blogTags", getBlogTags())
                .add("blogStatus", getBlogStatus())
                .add("enableComment", getEnableComment()).toString();
    }
}
