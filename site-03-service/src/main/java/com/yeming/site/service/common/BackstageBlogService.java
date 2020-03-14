package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageBlogDO;
import com.yeming.site.dao.entity.BackstageBlogTagRelationDO;
import com.yeming.site.dao.repository.BackstageBlogRepository;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.enums.SelectTypeEnum;
import com.yeming.site.util.enums.StatusEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yeming.gao
 * @Description: 博客表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageBlogService {

    private static Logger LOGGER = LoggerFactory.getLogger(BackstageBlogService.class);

    @Resource
    private BackstageBlogRepository backstageBlogRepository;

    @Resource
    private BackstageCategoryService backstageCategoryService;

    @Resource
    private BackstageBlogTagRelationService backstageBlogTagRelationService;

    /**
     * 获取指定删除状态的数量
     *
     * @param isDeleted 是否删除
     * @return Integer
     */
    public Integer getTotalBlogs(Integer isDeleted) {
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setIsDeleted(isDeleted);
        Example<BackstageBlogDO> example = Example.of(blogDO);
        long count = backstageBlogRepository.count(example);
        LOGGER.info("获取数据库中博客的数量,共{}条", count);
        return (int) count;
    }

    public List<BackstageBlogDO> findAll(Example<BackstageBlogDO> example, Sort sort) {
        return backstageBlogRepository.findAll(example, sort);
    }

    public void addBlogViews(Long id) {
        backstageBlogRepository.addBlogViews(id);
    }

    /**
     * 获取指定删除状态的数量
     *
     * @param id 是否删除
     * @return BackstageBlogDO
     */
    public BlogsBO getBlogById(Long id, Integer isDeleted) {
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setId(id);
        blogDO.setIsDeleted(isDeleted);
        Example<BackstageBlogDO> example = Example.of(blogDO);
        Optional<BackstageBlogDO> backstageBlogDO = backstageBlogRepository.findOne(example);

        if (backstageBlogDO.isPresent()) {
            BlogsBO blogsBO = new BlogsBO();
            BeanUtils.copyProperties(backstageBlogDO.get(), blogsBO);
            blogsBO.setBlogId(backstageBlogDO.get().getId().intValue());
            blogsBO.setBlogCoverImage(backstageBlogDO.get().getBlogCoverImage());
            //查询博客对应的标签
            List<BackstageBlogTagRelationDO> tags = backstageBlogTagRelationService.findRelationByBlogId(blogsBO.getBlogId());
            StringBuilder sb = new StringBuilder();
            tags.forEach(backstageBlogTagRelationDO -> {
                sb.append(backstageBlogTagRelationDO.getTagId()).append(AllConstants.Common.SPLIT_COMMA);
            });
            sb.deleteCharAt(sb.length() - 1);
            blogsBO.setBlogTags(sb.toString());
            return blogsBO;
        }
        return null;
    }

    /**
     * 获取指定删除状态的数量
     *
     * @param categoryId 是否删除
     * @return BackstageBlogDO
     */
    public List<BackstageBlogDO> findBlogsByCategoryId(Integer categoryId) {
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setBlogCategoryId(categoryId);
        Example<BackstageBlogDO> example = Example.of(blogDO);
        return backstageBlogRepository.findAll(example);
    }


    public void deleteById(Long id) {
        backstageBlogRepository.deleteById(id);
    }

    public BackstageBlogDO getById(Long id) {
        return backstageBlogRepository.findById(id).orElse(null);
    }


    public BackstageBlogDO conver(BlogsBO blogsBO) {
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setBlogTitle(blogsBO.getBlogTitle());
        blogDO.setBlogSubUrl(blogsBO.getBlogSubUrl());
        blogDO.setBlogCoverImage(blogsBO.getBlogCoverImage());
        blogDO.setBlogContent(blogsBO.getBlogContent());
        blogDO.setBlogCategoryId(blogsBO.getBlogCategoryId());
        blogDO.setBlogStatus(blogsBO.getBlogStatus());
        blogDO.setEnableComment(blogsBO.getEnableComment());
        blogDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        return blogDO;
    }

    public BackstageBlogDO saveOrUpdate(BackstageBlogDO blogDO) {
        return backstageBlogRepository.save(blogDO);
    }

    /**
     * 后台分页查询博客
     *
     * @param blogsBO 参数对象
     * @param keyword 查询条件，可能是标题也可能是分类
     */
    public void queryBlogByPageBackstage(String keyword, BlogsBO blogsBO) {
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(blogsBO.getPage(), blogsBO.getLimit(), sort);
        BackstageBlogDO blogDO = new BackstageBlogDO();
        blogDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageBlogDO> example = Example.of(blogDO);
        Page<BackstageBlogDO> page;
        if (StringUtils.isEmpty(keyword)) {
            page = backstageBlogRepository.findAll(example, pageable);
        } else {
            page = backstageBlogRepository.findByTitleOrCategoryName(keyword, pageable);
        }
        setResult(blogsBO,page);
    }

    /**
     * 前端分页查询博客（0-关键字查询，1-分类查询，2-标签查询）
     *
     * @param blogsBO 参数对象
     * @param keyword 查询条件，可能是标题也可能是分类
     */
    public void queryBlogByPageFront(String keyword, BlogsBO blogsBO) {
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(blogsBO.getPage(), blogsBO.getLimit(), sort);
        Page<BackstageBlogDO> page = null;
        //关键字查询
        if(SelectTypeEnum.KEY_WORD.getCode().equals(blogsBO.getSelectTpye())){
            if (StringUtils.isEmpty(keyword)) {
                BackstageBlogDO blogDO = new BackstageBlogDO();
                blogDO.setBlogStatus(StatusEnum.ONE.getCode());
                blogDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
                Example<BackstageBlogDO> example = Example.of(blogDO);
                page = backstageBlogRepository.findAll(example, pageable);
            } else {
                page = backstageBlogRepository.findByKeyWord(keyword, pageable);
            }
        }
        //分类查询
        if(SelectTypeEnum.CATEGORY.getCode().equals(blogsBO.getSelectTpye())){
            page = backstageBlogRepository.findByCategoryId(blogsBO.getBlogCategoryId(), pageable);
        }
        //标签查询
        if(SelectTypeEnum.TAG.getCode().equals(blogsBO.getSelectTpye())){
            page = backstageBlogRepository.findByTagId(blogsBO.getBlogTagId(), pageable);
        }

        if(Objects.isNull(page)){
            LOGGER.error("前端查询类型未知:",blogsBO.getSelectTpye());
            throw new SiteException("前端查询类型未知");
        }
        setResult(blogsBO,page);
    }

    private void setResult(BlogsBO blogsBO,Page<BackstageBlogDO> page){
        List<BackstageBlogDO> blogDOS = page.getContent();
        blogsBO.setTotalPage(page.getTotalPages());
        blogsBO.setTotalCount((int) page.getTotalElements());
        //查询所有博客分类
        Map<Long, String> allCategoryNameMap = backstageCategoryService.getAllCategoriesNameMap();
        //整合结果集
        List<BlogsBO> resultList = new ArrayList<>();
        blogDOS.forEach(backstageBlogDO -> {
            BlogsBO bo = covert(backstageBlogDO, allCategoryNameMap);
            bo.setAddTime(DateUtils.formatDateToStr(backstageBlogDO.getCreateTime(), blogsBO.getFormatDate()));
            resultList.add(bo);
        });
        blogsBO.setResultList(resultList);
    }

    private BlogsBO covert(BackstageBlogDO backstageBlogDO, Map<Long, String> allCategoryNameMap) {
        BlogsBO bo = new BlogsBO();
        bo.setBlogId(backstageBlogDO.getId().intValue());
        bo.setBlogTitle(backstageBlogDO.getBlogTitle());
        bo.setBlogContent(backstageBlogDO.getBlogContent());
        bo.setBlogCoverImage(backstageBlogDO.getBlogCoverImage());
        bo.setBlogViews(backstageBlogDO.getBlogViews());
        bo.setBlogStatus(backstageBlogDO.getBlogStatus());
        bo.setBlogCategoryId(backstageBlogDO.getBlogCategoryId());
        bo.setBlogCategoryName(allCategoryNameMap.get(backstageBlogDO.getBlogCategoryId().longValue()) == null
                ? "-" : allCategoryNameMap.get(backstageBlogDO.getBlogCategoryId().longValue()));
        return bo;
    }

}
