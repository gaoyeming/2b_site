package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageBlogCommentDO;
import com.yeming.site.dao.repository.BackstageBlogCommentRepository;
import com.yeming.site.service.dto.CommentsBO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.enums.StatusEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yeming.gao
 * @Description: 博客评论表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageBlogCommentService {

    private static Logger LOGGER = LoggerFactory.getLogger(BackstageBlogCommentService.class);

    @Resource
    private BackstageBlogCommentRepository backstageBlogCommentRepository;


    public Integer getTotalComments(Integer isDeleted) {
        BackstageBlogCommentDO blogCommentDO = new BackstageBlogCommentDO();
        blogCommentDO.setIsDeleted(isDeleted);
        Example<BackstageBlogCommentDO> example = Example.of(blogCommentDO);
        long count = backstageBlogCommentRepository.count(example);
        LOGGER.info("获取数据库中评论的数量,共{}条", count);
        return (int) count;
    }

    /**
     * 分页查询评论
     *
     * @param commentsBO 参数对象
     */
    public void queryBlogByPage(CommentsBO commentsBO) {
        Pageable pageable = PageRequest.of(commentsBO.getPage(), commentsBO.getLimit());
        BackstageBlogCommentDO commentDO = new BackstageBlogCommentDO();
        commentDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageBlogCommentDO> example = Example.of(commentDO);
        Page<BackstageBlogCommentDO> page = backstageBlogCommentRepository.findAll(example, pageable);
        List<BackstageBlogCommentDO> commentDOS = page.getContent();
        commentsBO.setTotalPage(page.getTotalPages());
        commentsBO.setTotalCount((int) page.getTotalElements());
        //整合结果集
        List<CommentsBO> resultList = new ArrayList<>();
        commentDOS.forEach(backstageBlogCommentDO -> {
            CommentsBO bo = new CommentsBO();
            bo.setCommentId(backstageBlogCommentDO.getId().intValue());
            bo.setCommentBody(backstageBlogCommentDO.getCommentBody());
            bo.setCommentator(backstageBlogCommentDO.getCommentator());
            bo.setCommentCreateTime(DateUtils.formatDateToStr(backstageBlogCommentDO.getCreateTime(), AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
            bo.setEmail(backstageBlogCommentDO.getEmail());
            bo.setCommentStatus(backstageBlogCommentDO.getCommentStatus());
            bo.setReplyBody(backstageBlogCommentDO.getReplyBody());
            bo.setReplyCreateTime(DateUtils.formatDateToStr(backstageBlogCommentDO.getReplyTime(), AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
            resultList.add(bo);
        });
        commentsBO.setResultList(resultList);
    }

    /**
     * 审核评论
     *
     * @param ids 需要审核得评论id
     */
    public void checkDone(Integer[] ids) {
        for (Integer id : ids) {
            backstageBlogCommentRepository.checkDone(id.longValue());
        }
    }

    /**
     * 删除评论
     *
     * @param ids 需要删除得评论id
     */
    public void deleteComments(Integer[] ids) {
        for (Integer id : ids) {
            backstageBlogCommentRepository.deleteById(id.longValue());
        }
    }

    /**
     * 回复评论
     *
     * @param commentId 需要恢复得id
     * @param replyBody 恢复内容
     */
    public void reply(Long commentId, String replyBody) {
        Optional<BackstageBlogCommentDO> commentDOOptional = backstageBlogCommentRepository.findById(commentId);
        if (!commentDOOptional.isPresent()) {
            throw new SiteException("当前回复的评论不存在");
        }
        if (!StatusEnum.ONE.getCode().equals(commentDOOptional.get().getCommentStatus())) {
            throw new SiteException("当前评论审核未通过不允许回复");
        }
        backstageBlogCommentRepository.reply(commentId, replyBody);
    }

    /**
     * 获取指定博客指定评论状态的数量
     *
     * @param blogId        博客id
     * @param commentStatus 评论状态
     * @return int
     */
    public int getTotalBlogComments(Integer blogId, Integer commentStatus) {
        BackstageBlogCommentDO commentDO = new BackstageBlogCommentDO();
        commentDO.setBlogId(blogId);
        commentDO.setCommentStatus(commentStatus);
        Example<BackstageBlogCommentDO> example = Example.of(commentDO);

        return (int) backstageBlogCommentRepository.count(example);
    }

    public void saveComments(CommentsBO commentsBO) {
        if(commentsBO.getBlogId()<0){
            LOGGER.error("请求的博客id:{}不正确",commentsBO.getBlogId());
            throw new SiteException("非法请求");
        }
        BackstageBlogCommentDO commentDO = convert(commentsBO);
        try {
            backstageBlogCommentRepository.save(commentDO);
        }catch (Exception e){
            LOGGER.error("插入评论出现异常:",e);
            throw new SiteException("对不起,评论失败");
        }

    }

    private BackstageBlogCommentDO convert(CommentsBO commentsBO){
        BackstageBlogCommentDO commentDO = new BackstageBlogCommentDO();
        commentDO.setBlogId(commentsBO.getBlogId().intValue());
        commentDO.setCommentator(commentsBO.getCommentator());
        commentDO.setEmail(commentsBO.getEmail());
        commentDO.setWebsiteUrl(commentsBO.getWebsiteUrl());
        commentDO.setCommentBody(commentsBO.getCommentBody());
        commentDO.setCommentatorIp(commentsBO.getCommentatorIp());
        commentDO.setCommentStatus(StatusEnum.ZERO.getCode());
        commentDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        return commentDO;
    }
}
