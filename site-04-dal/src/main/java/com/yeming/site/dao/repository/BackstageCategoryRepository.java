package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageCategoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeming.gao
 * @Description: 博客分类表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageCategoryRepository extends JpaRepository<BackstageCategoryDO, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE backstage_category SET category_rank=category_rank+1 WHERE id = ?1 AND is_deleted = 0", nativeQuery = true)
    int addRank(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE backstage_category SET category_rank=category_rank-1 WHERE id = ?1 AND is_deleted = 0", nativeQuery = true)
    int subRank(Long id);

}
