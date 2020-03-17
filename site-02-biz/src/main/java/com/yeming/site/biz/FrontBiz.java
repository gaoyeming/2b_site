package com.yeming.site.biz;

import com.yeming.site.dao.entity.BackstageBlogDO;
import com.yeming.site.dao.entity.BackstageCategoryDO;
import com.yeming.site.service.common.BackstageBlogCommentService;
import com.yeming.site.service.common.BackstageBlogService;
import com.yeming.site.service.common.BackstageCategoryService;
import com.yeming.site.service.common.BackstageTagService;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.service.dto.TagsBO;
import com.yeming.site.util.MarkDownUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.enums.StatusEnum;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 我的博客biz层
 * @date 2020/3/8 17:43
 */
@Component
public class FrontBiz {

    @Resource
    private BackstageBlogService backstageBlogService;

    @Resource
    private BackstageTagService backstageTagService;

    @Resource
    private BackstageCategoryService backstageCategoryService;

    @Resource
    private BackstageBlogCommentService backstageBlogCommentService;


    public List<BlogsBO> getBlogListForIndexPage(int type) {
        List<BlogsBO> blogsBOS = new ArrayList<>();
        Sort sort = null;
        if (StatusEnum.ZERO.getCode().equals(type)) {
            sort = Sort.by(Sort.Order.desc("blogViews"));
        }
        if (StatusEnum.ONE.getCode().equals(type)) {
            sort = Sort.by(Sort.Order.desc("createTime"));
        }
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        blogDO.setBlogStatus(StatusEnum.ONE.getCode());
        Example<BackstageBlogDO> example = Example.of(blogDO);
        List<BackstageBlogDO> blogDOS = backstageBlogService.findAll(example, sort);
        if (!CollectionUtils.isEmpty(blogDOS)) {
            blogDOS.forEach(backstageBlogDO -> {
                BlogsBO bo = new BlogsBO();
                bo.setBlogId(backstageBlogDO.getId().intValue());
                bo.setBlogTitle(backstageBlogDO.getBlogTitle());
                blogsBOS.add(bo);
            });
        }
        return blogsBOS;
    }

    public List<TagsBO> getTagUseCount() {
        return backstageTagService.getTagUseCount();
    }

    public BlogsBO getBlogDetail(Long id) {
        BlogsBO blogsBO = backstageBlogService.getBlogById(id, DeletedEnum.NO_DELETED.getCode());
        if (Objects.nonNull(blogsBO) && StatusEnum.ONE.getCode().equals(blogsBO.getBlogStatus())) {
            //设置标签说明集合
            List<TagsBO> tagNames = backstageTagService.getTagsName(blogsBO.getBlogTags().split(AllConstants.Common.SPLIT_COMMA));
            blogsBO.setBlogTagsDesc(tagNames);
            //增加浏览量
            backstageBlogService.addBlogViews(id);
            blogsBO.setBlogViews(blogsBO.getBlogViews() + 1);
            //格式化内容
            blogsBO.setBlogContent(MarkDownUtils.mdToHtml(blogsBO.getBlogContent()));
            //查询当前分类是否存在
            BackstageCategoryDO categoryDO = backstageCategoryService.getCategoryById(blogsBO.getBlogCategoryId().longValue(), DeletedEnum.NO_DELETED.getCode());
            if (Objects.isNull(categoryDO)) {
                categoryDO = new BackstageCategoryDO();
                categoryDO.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            blogsBO.setBlogCategoryIcon(categoryDO.getCategoryIcon());
            //设置评论数
            blogsBO.setCommentCount(backstageBlogCommentService.getTotalBlogComments(blogsBO.getBlogId(), StatusEnum.ONE.getCode()));
            return blogsBO;
        }

        return null;

    }


}
