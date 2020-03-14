package com.yeming.site.dao.repository;

import com.yeming.site.dao.entity.BackstageBlogCommentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeming.gao
 * @Description: 博客评论表的Repository
 * @date 2019/11/6 14:02
 */
public interface BackstageBlogCommentRepository extends JpaRepository<BackstageBlogCommentDO, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE backstage_blog_comment SET comment_status=1 WHERE id = ?1 AND comment_status = 0", nativeQuery = true)
    int checkDone(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE backstage_blog_comment SET is_replyed=1,reply_body=?2,reply_time=SYSDATE() WHERE id = ?1 AND comment_status = 1", nativeQuery = true)
    int reply(Long id, String replyBody);

}
