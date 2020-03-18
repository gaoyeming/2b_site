package com.yeming.site.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yeming.gao
 * @Description: 留言信息表
 * @date 2019/11/26 15:38
 */
@Table(name = "leave_message")
@Setter
@Getter
@Entity
public class LeaveMessageDO extends BaseDO implements Serializable {

    /**
     * 姓名
     **/
    @Column
    private String name;
    /**
     * 邮箱
     **/
    @Column
    private String email;
    /**
     * 留言
     **/
    @Column
    private String message;


    @Override
    public String toString() {
        return super.toStringHelper()
                .add("name", getName())
                .add("email", getEmail())
                .add("message", getMessage()).toString();
    }
}
