package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageLinkDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yeming.gao
 * @Description: 博客友链表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageLinkRepository extends JpaRepository<BackstageLinkDO, Long> {

}
