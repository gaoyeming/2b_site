package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageBlogDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeming.gao
 * @Description: 博客表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageBlogRepository extends JpaRepository<BackstageBlogDO, Long> {

    @Query(value = "SELECT new BackstageBlogDO(b.id,b.blogTitle,b.blogSubUrl,b.blogCoverImage,b.blogContent,b.blogCategoryId,b.blogStatus,b.blogViews,b.enableComment,b.createTime) " +
            "FROM BackstageBlogDO b,BackstageCategoryDO bc\n" +
            "WHERE b.blogCategoryId=bc.id AND b.isDeleted=0 AND bc.isDeleted=0 AND\n" +
            "(b.blogTitle LIKE %?1% OR bc.categoryName LIKE %?1%)")
    Page<BackstageBlogDO> findByTitleOrCategoryName(String keyword, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE backstage_blog SET blog_views=blog_views+1 WHERE id = ?1 AND is_deleted = 0", nativeQuery = true)
    int addBlogViews(Long id);

    @Query(value = "SELECT new BackstageBlogDO(b.id,b.blogTitle,b.blogSubUrl,b.blogCoverImage,b.blogContent,b.blogCategoryId,b.blogStatus,b.blogViews,b.enableComment,b.createTime) " +
            "FROM BackstageBlogDO b,BackstageCategoryDO bc\n" +
            "WHERE b.blogCategoryId=bc.id AND b.isDeleted=0 AND bc.isDeleted=0 AND b.blogStatus=1 AND\n" +
            "(b.blogTitle LIKE %?1% OR bc.categoryName LIKE %?1%)")
    Page<BackstageBlogDO> findByKeyWord(String keyword, Pageable pageable);

    @Query(value = "SELECT new BackstageBlogDO(b.id,b.blogTitle,b.blogSubUrl,b.blogCoverImage,b.blogContent,b.blogCategoryId,b.blogStatus,b.blogViews,b.enableComment,b.createTime) " +
            "FROM BackstageBlogDO b\n" +
            "WHERE b.blogCategoryId=?1 AND b.isDeleted=0 AND b.blogStatus=1")
    Page<BackstageBlogDO> findByCategoryId(Integer categoryId, Pageable pageable);

    @Query(value = "SELECT new BackstageBlogDO(b.id,b.blogTitle,b.blogSubUrl,b.blogCoverImage,b.blogContent,b.blogCategoryId,b.blogStatus,b.blogViews,b.enableComment,b.createTime) " +
            "FROM BackstageBlogDO b\n" +
            "WHERE b.id IN (SELECT DISTINCT blogId FROM BackstageBlogTagRelationDO WHERE tagId = ?1) AND b.blogStatus=1")
    Page<BackstageBlogDO> findByTagId(Integer tagId, Pageable pageable);

}
