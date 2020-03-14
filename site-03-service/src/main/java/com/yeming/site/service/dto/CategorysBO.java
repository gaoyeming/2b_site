package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 分类传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class CategorysBO extends BaseBO {

    private Integer categoryId;
    private String categoryName;
    private String categoryIcon;
    private String addTime;

    /**
     * 分类查询结果集合
     */
    private List<CategorysBO> resultList = new ArrayList<>();
}
