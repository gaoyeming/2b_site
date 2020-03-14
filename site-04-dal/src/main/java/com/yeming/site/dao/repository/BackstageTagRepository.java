package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageTagDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 博客标签表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageTagRepository extends JpaRepository<BackstageTagDO, Long> {

    @Query(value = "SELECT t.id AS tagId,t.tag_name AS tagName,IFNULL(btr.c,0) AS tagUseCount FROM backstage_tag  t\n" +
            "LEFT JOIN (SELECT tag_id,count(1)  c FROM backstage_blog_tag_relation GROUP BY tag_id) btr ON t.id=btr.tag_id\n" +
            "WHERE t.is_deleted = 0 ORDER BY tagUseCount  DESC;",nativeQuery = true)
    List<Map<String,Object>> getTagUseCount();
}
