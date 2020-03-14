package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeming.gao
 * @Description: 系统配置传输对象
 * @date 2020/2/28 11:22
 */
@Setter
@Getter
public class SysConfigBO extends BaseBO {

    private String configName;
    private String configValue;
    private String configDesc;

    /**
     * 分类查询结果集合
     */
    private List<SysConfigBO> resultList = new ArrayList<>();
}
