package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageUserDO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author yeming.gao
 * @Description: 后台管理系统登陆用户信息表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageUserRepository extends JpaRepository<BackstageUserDO, Long> {

}
