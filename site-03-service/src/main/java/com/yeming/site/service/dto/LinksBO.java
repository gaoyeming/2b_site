package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 友情链接传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class LinksBO extends BaseBO {

    private Integer linkType;
    private Integer linkId;
    private String linkName;
    private String linkUrl;
    private String linkDescription;
    private Integer linkRank;
    private String addTime;

    /**
     * 友链查询结果集合
     */
    private List<LinksBO> resultList = new ArrayList<>();
}
