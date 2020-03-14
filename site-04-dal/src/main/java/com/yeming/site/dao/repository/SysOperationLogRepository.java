package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.SysOperationLogDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yeming.gao
 * @Description: 操作日志的Repository
 * @date 2019/11/6 14:02
 */
public interface SysOperationLogRepository extends JpaRepository<SysOperationLogDO, Long> {
}
