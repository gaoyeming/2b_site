package com.yeming.site.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yeming.site.dao.entity.BackstageBlogDO;
import com.yeming.site.dao.entity.SysConfigDO;
import com.yeming.site.dao.repository.BackstageBlogRepository;
import com.yeming.site.service.common.*;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.service.dto.SysConfigBO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yeming.gao
 * @Description: 系统配置
 * @date 2020/3/6 9:39
 */
@Component
public class BacksatgeBiz {
    private static Logger LOGGER = LoggerFactory.getLogger(BacksatgeBiz.class);

    private static final String WEBSITE_NAME = "personal site";
    private static final String WEBSITE_DESCRIPTION = "personal site是SpringBoot2+Thymeleaf+JPA建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建";
    private static final String WEBSITE_LOGO = "/admin/dist/img/logo2.png";
    private static final String WEBSITE_ICON = "/admin/dist/img/favicon.png";

    private static final String YOUR_AVATAR = "/admin/dist/img/13.png";
    private static final String YOUR_EMAIL = "yeming.gao@aliyun.com";
    private static final String YOUR_NAME = "我爱2B哥";

    private static final String FOOTER_ABOUT = "your personal blog. have fun.";
    private static final String FOOTERICP = "浙ICP备 xxxxxx-x号";
    private static final String FOOTER_COPY_RIGHT = "@2020 我爱2B哥";
    private static final String FOOTER_POWERED_BY = "I LOVE 2B BROTHER";
    private static final String FOOTER_POWERED_BYURL = "##";

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private BackstageTagService backstageTagService;

    @Resource
    private BackstageCategoryService backstageCategoryService;

    @Resource
    private BackstageBlogService backstageBlogService;

    @Resource
    private BackstageBlogTagRelationService backstageBlogTagRelationService;


    /**
     * 获取所有系统配置信息
     *
     * @return Map<String                                                                                                                               ,                                                                                                                                                                                                                                                               String>
     */
    public Map<String, String> getAllConfigs() {
        List<SysConfigDO> configDOS = sysConfigService.getAll();
        if (Objects.isNull(configDOS)
                || configDOS.size() == 0) {
            LOGGER.warn("当前无系统配置信息");
            return null;
        }
        LOGGER.info("获取系统配置信息:{}", JSON.toJSONString(configDOS));
        Map<String, String> configMap = configDOS.stream()
                .collect(Collectors.toMap(SysConfigDO::getConfigName, SysConfigDO::getConfigValue));
        configMap.forEach((key, value) -> {
            if (AllConstants.SysConfig.WEBSITE_NAME.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, WEBSITE_NAME);
            }
            if (AllConstants.SysConfig.WEBSITE_DESCRIPTION.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, WEBSITE_DESCRIPTION);
            }
            if (AllConstants.SysConfig.WEBSITE_LOGO.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, WEBSITE_LOGO);
            }
            if (AllConstants.SysConfig.WEBSITE_ICON.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, WEBSITE_ICON);
            }
            if (AllConstants.SysConfig.YOUR_AVATAR.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, YOUR_AVATAR);
            }
            if (AllConstants.SysConfig.YOUR_EMAIL.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, YOUR_EMAIL);
            }
            if (AllConstants.SysConfig.YOUR_NAME.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, YOUR_NAME);
            }
            if (AllConstants.SysConfig.FOOTER_ABOUT.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, FOOTER_ABOUT);
            }
            if (AllConstants.SysConfig.FOOTER_ICP.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, FOOTERICP);
            }
            if (AllConstants.SysConfig.FOOTER_COPYRIGHT.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, FOOTER_COPY_RIGHT);
            }
            if (AllConstants.SysConfig.FOOTER_POWEREDBY.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, FOOTER_POWERED_BY);
            }
            if (AllConstants.SysConfig.FOOTER_POWEREDBYURL.equals(key) && StringUtils.isEmpty(value)) {
                configMap.put(key, FOOTER_POWERED_BYURL);
            }
        });

        return configMap;
    }

    /**
     * 以json串格式更新系统配置信息
     *
     * @param sysConfig 需要更新的串
     */
    public void updateConfig(String sysConfig) {
        JSONObject jsonObject = JSONObject.parseObject(sysConfig);
        Map<String, Object> sysConfigMap = jsonObject.getInnerMap();
        List<SysConfigBO> sysConfigBOList = new ArrayList<>();
        sysConfigMap.forEach((key, value) -> {
            SysConfigBO sysConfigBO = new SysConfigBO();
            sysConfigBO.setConfigName(key);
            sysConfigBO.setConfigValue(String.valueOf(value));
            sysConfigBOList.add(sysConfigBO);
        });
        LOGGER.info("更新系统配置信息:{}", JSON.toJSONString(sysConfigBOList));
        sysConfigService.updateConfig(sysConfigBOList);
    }

    /**
     * 新增博客
     *
     * @param blogsBO 参数对象
     */
    public void saveBlog(BlogsBO blogsBO) {
        //判断标签信息
        backstageTagService.isTags(blogsBO.getBlogTags().split(AllConstants.Common.SPLIT_COMMA));
        LOGGER.info("{}标签判断成功", blogsBO.getBlogTags());
        //判断分类信息
        backstageCategoryService.isCategory(blogsBO.getBlogCategoryId());
        LOGGER.info("{}分类判断成功", blogsBO.getBlogCategoryId());
        BackstageBlogDO blogDO = backstageBlogService.conver(blogsBO);
        blogDO = backstageBlogService.saveOrUpdate(blogDO);
        LOGGER.info("{}博客新增成功", JSON.toJSONString(blogDO));
        //新增标签关联信息
        backstageBlogTagRelationService.saveRelation(blogDO.getId().intValue(), blogsBO.getBlogTags().split(AllConstants.Common.SPLIT_COMMA));
        LOGGER.info("blogId={}、tagIds={}博客标签关联成功", blogDO.getId(), blogsBO.getBlogTags());
        //分类表categoryRank+1
        backstageCategoryService.addRank(blogsBO.getBlogCategoryId());
        LOGGER.info("id={}对应的分类等级成功加一", blogsBO.getBlogCategoryId());
    }

    /**
     * 更新博客
     *
     * @param blogsBO 参数对象
     */
    public void updateBlog(BlogsBO blogsBO) {
        //首先判断标签信息
        backstageTagService.isTags(blogsBO.getBlogTags().split(AllConstants.Common.SPLIT_COMMA));
        LOGGER.info("{}标签判断成功", blogsBO.getBlogTags());
        //判断分类信息
        backstageCategoryService.isCategory(blogsBO.getBlogCategoryId());
        LOGGER.info("{}分类判断成功", blogsBO.getBlogCategoryId());
        BackstageBlogDO orgBlogDO = backstageBlogService.getById(blogsBO.getId());
        if (Objects.isNull(orgBlogDO)) {
            LOGGER.error(blogsBO.getId() + ":博客不存在");
            throw new SiteException("当前更新的博客不存在,无需更新");
        }
        Integer orgCategoryId = orgBlogDO.getBlogCategoryId();
        BackstageBlogDO blogDO = backstageBlogService.conver(blogsBO);
        blogDO.setId(blogsBO.getId());
        blogDO = backstageBlogService.saveOrUpdate(blogDO);
        LOGGER.info("{}博客更新成功", JSON.toJSONString(blogDO));
        //更新标签关联信息
        backstageBlogTagRelationService.updateRelation(blogDO.getId().intValue(), blogsBO.getBlogTags().split(AllConstants.Common.SPLIT_COMMA));
        LOGGER.info("blogId={}、tagIds={}博客标签关联成功", blogDO.getId(), blogsBO.getBlogTags());
        //分类表更新，原先的categoryRank-1；新的categoryRank+1
        if (!orgCategoryId.equals(blogDO.getBlogCategoryId())) {
            backstageCategoryService.subRank(orgCategoryId);
            LOGGER.info("原先的id={}对应的分类等级成功减一", orgCategoryId);
            backstageCategoryService.addRank(blogDO.getBlogCategoryId());
            LOGGER.info("新的id={}对应的分类等级成功加一", blogDO.getBlogCategoryId());
        }
    }

    /**
     * 删除博客信息
     *
     * @param ids 需要删除的博客主键
     */
    public void deleteBlog(Integer[] ids) {
        for (Integer id : ids) {
            BackstageBlogDO blogDO = backstageBlogService.getById(id.longValue());
            if (Objects.isNull(blogDO)) {
                LOGGER.error(id + ":博客不存在");
                throw new SiteException("博客有所更新,请先刷新在操作");
            }
            backstageBlogService.deleteById(id.longValue());
            LOGGER.info("{}博客删除成功", JSON.toJSONString(blogDO));
            backstageBlogTagRelationService.deleteRelation(id);
            LOGGER.info("blogId={}对应的博客标签关联删除成功", id);
            backstageCategoryService.subRank(blogDO.getBlogCategoryId());
            LOGGER.info("id={}对应的分类等级成功减一", blogDO.getBlogCategoryId());
        }
    }

}
