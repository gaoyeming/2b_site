package com.yeming.site.controller.vo.request;

import com.yeming.site.aop.annotation.EnumValidator;
import com.yeming.site.service.dto.LinksBO;
import com.yeming.site.util.enums.LinkTypeEnum;
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
 * @Description: 友链请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "linksVO对象", description = "友链操作对象")
@Setter
@Getter
public class LinksVO extends CommonVO {

    /**
     * 0-草稿 1-发布
     **/
    @ApiModelProperty(value = "友链类型", name = "linkType", example = "友链类型选值:0-友链/1-推荐/2-个人网站")
    @NotNull(message = "请输入友链类型")
    @EnumValidator(enumClass = LinkTypeEnum.class, message = "友链类型选值范围:0-友链/1-推荐/2-个人网站")
    private Integer linkType;

    @ApiModelProperty(value = "网站名称", name = "linkName", example = "狼人组博客")
    @NotEmpty(message = "请输入网站名称")
    @Length(max = 50, message = "网站名称过长")
    private String linkName;

    @ApiModelProperty(value = "网站链接", name = "linkUrl", example = "http://zjay.xn--6qq986b3xl:8888/")
    @NotEmpty(message = "请输入网站链接")
    @Length(max = 100, message = "网站链接过长")
    private String linkUrl;

    @ApiModelProperty(value = "网站描述", name = "linkDescription", example = "XXXXXXXXXX......")
    @NotEmpty(message = "请输入网站描述")
    @Length(max = 100, message = "网站描述过长")
    private String linkDescription;

    @ApiModelProperty(value = "网站等级", name = "linkRank", example = "1")
    @NotNull(message = "请输入网站等级")
    private Integer linkRank;

    /**
     * 友链查询结果集合
     */
    private List<LinksBO> resultList;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("linkType", getLinkType())
                .add("linkName", getLinkName())
                .add("linkUrl", getLinkUrl())
                .add("linkDescription", getLinkDescription())
                .add("linkRank", getLinkRank()).toString();
    }
}
