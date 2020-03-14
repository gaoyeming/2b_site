package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 标签传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class TagsBO extends BaseBO {

    private Integer tagId;
    private String tagName;
    private String addTime;
    private Integer tagUseCount;
    private Boolean isSelected;

    /**
     * 标签查询结果集合
     */
    private List<TagsBO> resultList = new ArrayList<>();
}
