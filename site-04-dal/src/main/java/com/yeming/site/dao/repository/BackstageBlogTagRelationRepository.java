package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageBlogTagRelationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeming.gao
 * @Description: 博客标签表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageBlogTagRelationRepository extends JpaRepository<BackstageBlogTagRelationDO, Long> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM backstage_blog_tag_relation WHERE blog_id = ?1",nativeQuery=true)
    void deleteByBlogId(Integer blogId);
}
