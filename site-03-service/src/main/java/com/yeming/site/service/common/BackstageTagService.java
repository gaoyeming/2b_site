package com.yeming.site.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yeming.site.dao.entity.BackstageBlogTagRelationDO;
import com.yeming.site.dao.entity.BackstageTagDO;
import com.yeming.site.dao.repository.BackstageTagRepository;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.service.dto.TagsBO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.exception.SiteException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yeming.gao
 * @Description: 博客标签表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageTagService {

    private static Logger LOGGER = LoggerFactory.getLogger(BackstageTagService.class);

    @Resource
    private BackstageBlogTagRelationService backstageBlogTagRelationService;

    @Resource
    private BackstageTagRepository backstageTagRepository;


    public Integer getTotalTags(Integer isDeleted) {
        BackstageTagDO blogTagDO = new BackstageTagDO();
        blogTagDO.setIsDeleted(isDeleted);
        Example<BackstageTagDO> example = Example.of(blogTagDO);
        long count = backstageTagRepository.count(example);
        LOGGER.info("获取数据库中标签的数量,共{}条", count);
        return (int) count;
    }

    /**
     * 分页查询标签
     *
     * @param tagsBO 参数对象
     */
    public void queryTagsByPage(TagsBO tagsBO) {
        Pageable pageable = PageRequest.of(tagsBO.getPage(), tagsBO.getLimit());
        BackstageTagDO tagDO = new BackstageTagDO();
        tagDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageTagDO> example = Example.of(tagDO);
        Page<BackstageTagDO> page = backstageTagRepository.findAll(example, pageable);
        List<BackstageTagDO> tagDOS = page.getContent();
        tagsBO.setTotalPage(page.getTotalPages());
        tagsBO.setTotalCount((int) page.getTotalElements());
        //整合结果集
        List<TagsBO> resultList = new ArrayList<>();
        tagDOS.forEach(backstageTagDO -> {
            TagsBO bo = new TagsBO();
            bo.setTagId(backstageTagDO.getId().intValue());
            bo.setTagName(backstageTagDO.getTagName());
            bo.setAddTime(DateUtils.formatDateToStr(backstageTagDO.getCreateTime(), AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
            resultList.add(bo);
        });
        tagsBO.setResultList(resultList);
    }

    /**
     * 新增或者更新标签信息
     *
     * @param tagsBO 参数对象
     */
    public void saveOrUpdateBlog(TagsBO tagsBO) {
        BackstageTagDO tagDO = conver(tagsBO);
        tagDO.setId(tagsBO.getId());
        if (Objects.isNull(tagDO.getId())) {
            //表示为新增，需要判断标签名称是否重复
            Example<BackstageTagDO> example = Example.of(tagDO);
            Optional<BackstageTagDO> tagDOOptional = backstageTagRepository.findOne(example);
            if (tagDOOptional.isPresent()) {
                throw new SiteException("当前标签名称已存在");
            }
        }
        backstageTagRepository.save(tagDO);
    }

    private BackstageTagDO conver(TagsBO tagsBO) {
        BackstageTagDO tagDO = new BackstageTagDO();
        tagDO.setTagName(tagsBO.getTagName());
        return tagDO;
    }

    /**
     * 删除标签
     *
     * @param ids 需要删除得标签id
     */
    public void deleteTags(Integer[] ids) {
        for (Integer id : ids) {
            List<BackstageBlogTagRelationDO> relationDOS = backstageBlogTagRelationService.findRelationByTagId(id);
            Optional<BackstageTagDO> tagDOOptional = backstageTagRepository.findById(id.longValue());
            String tagName = tagDOOptional.isPresent() ? tagDOOptional.get().getTagName() : "";
            if (Objects.nonNull(relationDOS) && relationDOS.size() > 0) {
                throw new SiteException(tagName + ":该标签存在博客正在使用,不允许删除");
            }
            backstageTagRepository.deleteById(id.longValue());
        }
    }

    /**
     * 查询所有的博客标签表
     * 按照categoryRank、createTime倒序
     *
     * @return List<BackstageCategoryDO>
     */
    private List<BackstageTagDO> getAllTags(Integer isDeleted) {
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        BackstageTagDO blogTagDO = new BackstageTagDO();
        blogTagDO.setIsDeleted(isDeleted);
        Example<BackstageTagDO> example = Example.of(blogTagDO);
        return backstageTagRepository.findAll(example, sort);
    }

    /**
     * 查询所有的博客标签表
     * 按照categoryRank、createTime倒序
     *
     * @return List<BackstageCategoryDO>
     */
    public List<TagsBO> getAllTags() {
        List<TagsBO> relsultList = new ArrayList<>();
        List<BackstageTagDO> tagDOList = getAllTags(DeletedEnum.NO_DELETED.getCode());
        if (Objects.nonNull(tagDOList) && tagDOList.size() > 0) {
            tagDOList.forEach(backstageTagDO -> {
                TagsBO bo = new TagsBO();
                bo.setTagId(backstageTagDO.getId().intValue());
                bo.setTagName(backstageTagDO.getTagName());
                relsultList.add(bo);
            });
        }
        return relsultList;
    }

    /**
     * 查询指定博客的哪些标签是否选中
     *
     * @param blog 指定的博客
     * @return List<TagsBO>
     */
    public List<TagsBO> findIsSelected(BlogsBO blog) {
        String[] blogTags = blog.getBlogTags().split(AllConstants.Common.SPLIT_COMMA);
        List<TagsBO> relsultList = new ArrayList<>();
        List<BackstageTagDO> tagDOList = getAllTags(DeletedEnum.NO_DELETED.getCode());
        if (Objects.nonNull(tagDOList) && tagDOList.size() > 0) {
            tagDOList.forEach(backstageTagDO -> {
                TagsBO bo = new TagsBO();
                bo.setTagId(backstageTagDO.getId().intValue());
                bo.setTagName(backstageTagDO.getTagName());
                bo.setIsSelected(false);
                for (String blogTag : blogTags) {
                    if (blogTag.equals(String.valueOf(backstageTagDO.getId()))) {
                        bo.setIsSelected(true);
                    }
                }
                relsultList.add(bo);
            });
        }
        return relsultList;
    }

    /**
     * 查询所有标签使用数量(前20个)
     *
     * @return List<TagsBO>
     */
    public List<TagsBO> getTagUseCount() {

        List<Map<String, Object>> tagDOListMap = backstageTagRepository.getTagUseCount();
        if (Objects.isNull(tagDOListMap) || tagDOListMap.size() == 0) {
            return null;
        }
        List<TagsBO> tagsBOS = new ArrayList<>();
        tagDOListMap.forEach(object -> {
            TagsBO bo = JSONObject.parseObject(JSON.toJSONString(object), TagsBO.class);
            if (bo.getTagUseCount() > 0) {
                tagsBOS.add(bo);
            }
        });
        return tagsBOS;
    }

    /**
     * 判断标签是否存在
     *
     * @param tags 标签数组
     */
    public void isTags(String[] tags) {
        StringBuilder errorStr = new StringBuilder();
        for (String tag : tags) {
            try {
                long tagId = Long.parseLong(tag);
                Optional<BackstageTagDO> tagDOOptional = backstageTagRepository.findById(tagId);
                if (!tagDOOptional.isPresent()) {
                    errorStr.append(tag).append(":标签不存在").append(AllConstants.Common.SPLIT_KIND);
                } else {
                    if (!DeletedEnum.NO_DELETED.getCode().equals(tagDOOptional.get().getIsDeleted())) {
                        errorStr.append(tag).append(":标签状态不正确").append(AllConstants.Common.SPLIT_KIND);
                    }
                }
            } catch (NumberFormatException e) {
                errorStr.append(tag).append(":标签ID错误").append(AllConstants.Common.SPLIT_KIND);
            }
        }

        if (StringUtils.isNotEmpty(errorStr.toString())) {
            LOGGER.error("标签判断失败:" + errorStr.toString());
            throw new SiteException(errorStr.toString());
        }
    }

    public List<TagsBO> getTagsName(String[] tagIds) {
        List<TagsBO> tags = new ArrayList<>();
        for (String tag : tagIds) {
            try {
                long tagId = Long.parseLong(tag);
                Optional<BackstageTagDO> tagDOOptional = backstageTagRepository.findById(tagId);
                if (!tagDOOptional.isPresent()) {
                    LOGGER.error("标签不存在:" + tag);
                } else {
                    if (!DeletedEnum.NO_DELETED.getCode().equals(tagDOOptional.get().getIsDeleted())) {
                        LOGGER.error("标签状态不正确:" + tag);
                    }
                    TagsBO bo = new TagsBO();
                    bo.setTagId(tagDOOptional.get().getId().intValue());
                    bo.setTagName(tagDOOptional.get().getTagName());
                    tags.add(bo);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("标签ID错误:" + tag);
            }
        }

        return tags;
    }
}
