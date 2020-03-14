package com.yeming.site.controller.vo.request;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yeming.gao
 * @Description: 博客请求公共对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "公共对象", description = "分页查询页码对象")
@Setter
@Getter
public class CommonVO {

    @ApiModelProperty(value = "当前页码", name = "page", example = "0")
    private Integer page;

    @ApiModelProperty(value = "每页条数", name = "limit", example = "30")
    private Integer limit;

    @ApiModelProperty(value = "排序方式", name = "order", example = "asc")
    private String order;

    @ApiModelProperty(value = "当前页", name = "currPage", example = "1")
    private Integer currPage;

    @ApiModelProperty(value = "总页数", name = "totalPage", example = "10")
    private Integer totalPage;

    @ApiModelProperty(value = "总条数", name = "totalCount", example = "300")
    private Integer totalCount;

    MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("page", getPage())
                .add("limit", getLimit())
                .add("order", getOrder())
                .add("currPage", getCurrPage())
                .add("totalPage", getTotalPage())
                .add("totalCount", getTotalCount());
    }


}
