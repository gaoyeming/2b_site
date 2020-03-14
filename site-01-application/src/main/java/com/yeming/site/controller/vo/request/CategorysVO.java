package com.yeming.site.controller.vo.request;

import com.google.common.base.MoreObjects;
import com.yeming.site.service.dto.CategorysBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 分类请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "categorysVO对象", description = "分类操作对象")
@Setter
@Getter
public class CategorysVO extends CommonVO {

    @ApiModelProperty(value = "分类名称", name = "categoryName", example = "日记")
    @NotEmpty(message = "请输入分类名称")
    @Length(max = 50, message = "分类名称过长")
    private String categoryName;

    @ApiModelProperty(value = "分类图标", name = "categoryIcon", example = "http://127.0.0.1:8080/admin/dist/img/category/03.png")
    @NotEmpty(message = "请输入分类图标")
    @Length(max = 200, message = "分类图标过长")
    private String categoryIcon;

    /**
     * 分类查询结果集合
     */
    private List<CategorysBO> resultList;


    @Override
    public String toString() {
        return super.toStringHelper()
                .add("categoryName", getCategoryName())
                .add("categoryIcon", getCategoryIcon()).toString();
    }
}
