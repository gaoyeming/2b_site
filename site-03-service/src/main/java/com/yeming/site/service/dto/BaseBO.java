package com.yeming.site.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yeming.gao
 * @Description: 共有的传输对象
 * @date 2019/11/6 14:53
 */
@Setter
@Getter
public class BaseBO {
    /**
     * 用户数据库主键
     **/
    private Long id;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 更新时间
     **/
    private Date updateTime;
    /**
     * 返回码
     **/
    private String code;
    /**
     * 返回描述
     **/
    private String message;

    /**
     * 分页查询需要用到的条件
     */
    private Integer page;
    private Integer limit;
    private String order;
    private Integer currPage;
    private Integer totalPage;
    private Integer totalCount;
}
