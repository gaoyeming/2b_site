package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.LeaveMessageDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yeming.gao
 * @Description: 留言信息的Repository
 * @date 2019/11/6 14:02
 */
public interface LeaveMessageRepository extends JpaRepository<LeaveMessageDO, Long> {
}
