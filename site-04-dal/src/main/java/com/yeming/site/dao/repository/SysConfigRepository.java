package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.SysConfigDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yeming.gao
 * @Description: 系统配置的Repository
 * @date 2019/11/6 14:02
 */
public interface SysConfigRepository extends JpaRepository<SysConfigDO, Long> {
}
