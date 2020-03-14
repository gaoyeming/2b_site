package com.yeming.site.controller.vo.request;

import com.yeming.site.service.dto.TagsBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 标签请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "tagsVO对象", description = "标签操作对象")
@Setter
@Getter
public class TagsVO extends CommonVO{

    @ApiModelProperty(value = "标签名称", name = "tagName", example = "JAVA")
    @NotEmpty(message = "请输入标签名称")
    @Length(max = 50, message = "标签名称过长")
    private String tagName;
    /**
     * 标签查询结果集合
     */
    private List<TagsBO> resultList;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("tagName", getTagName()).toString();
    }
}
