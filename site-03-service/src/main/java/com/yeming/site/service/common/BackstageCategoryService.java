package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageBlogDO;
import com.yeming.site.dao.entity.BackstageCategoryDO;
import com.yeming.site.dao.repository.BackstageCategoryRepository;
import com.yeming.site.service.dto.CategorysBO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yeming.gao
 * @Description: 博客分类表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageCategoryService {

    private static Logger LOGGER = LoggerFactory.getLogger(BackstageCategoryService.class);

    @Resource
    private BackstageBlogService backstageBlogService;
    @Resource
    private BackstageCategoryRepository backstageCategoryRepository;


    public Integer getTotalCategories(Integer isDeleted) {
        BackstageCategoryDO blogCategoryDO = new BackstageCategoryDO();
        blogCategoryDO.setIsDeleted(isDeleted);
        Example<BackstageCategoryDO> example = Example.of(blogCategoryDO);
        long count = backstageCategoryRepository.count(example);
        LOGGER.info("获取数据库中分类的数量,共{}条", count);
        return (int) count;
    }

    /**
     * 查询所有的博客分类表
     * 按照categoryRank、createTime倒序
     *
     * @return List<BackstageCategoryDO>
     */
    public List<BackstageCategoryDO> getAllCategories(Integer isDeleted) {
        Sort sort = Sort.by(Sort.Order.desc("categoryRank"), Sort.Order.desc("createTime"));
        BackstageCategoryDO blogCategoryDO = new BackstageCategoryDO();
        blogCategoryDO.setIsDeleted(isDeleted);
        Example<BackstageCategoryDO> example = Example.of(blogCategoryDO);
        return backstageCategoryRepository.findAll(example, sort);
    }

    /**
     * 查询所有的博客分类表id,name
     *
     * @return Map<String       ,       String>
     */
    public Map<Long, String> getAllCategoriesNameMap() {
        List<BackstageCategoryDO> allCategoryDO = getAllCategories(DeletedEnum.NO_DELETED.getCode());
        return allCategoryDO.stream().collect(Collectors.toMap(BackstageCategoryDO::getId, BackstageCategoryDO::getCategoryName));
    }

    /**
     * 分页查询分类
     *
     * @param categorysBO 参数对象
     */
    public void queryBlogByPage(CategorysBO categorysBO) {
        Pageable pageable = PageRequest.of(categorysBO.getPage(), categorysBO.getLimit());
        BackstageCategoryDO categoryDO = new BackstageCategoryDO();
        categoryDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageCategoryDO> example = Example.of(categoryDO);
        Page<BackstageCategoryDO> page = backstageCategoryRepository.findAll(example, pageable);
        List<BackstageCategoryDO> categoryDOS = page.getContent();
        categorysBO.setTotalPage(page.getTotalPages());
        categorysBO.setTotalCount((int) page.getTotalElements());
        //整合结果集
        List<CategorysBO> resultList = new ArrayList<>();
        categoryDOS.forEach(backstageCategoryDO -> {
            CategorysBO bo = new CategorysBO();
            bo.setCategoryId(backstageCategoryDO.getId().intValue());
            bo.setCategoryName(backstageCategoryDO.getCategoryName());
            bo.setCategoryIcon(backstageCategoryDO.getCategoryIcon());
            bo.setAddTime(DateUtils.formatDateToStr(backstageCategoryDO.getCreateTime(), AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
            resultList.add(bo);
        });
        categorysBO.setResultList(resultList);
    }

    /**
     * 新增或者更新分类信息
     *
     * @param categorysBO 参数对象
     */
    public void saveOrUpdateBlog(CategorysBO categorysBO) {
        BackstageCategoryDO categoryDO = conver(categorysBO);
        categoryDO.setId(categorysBO.getId());
        backstageCategoryRepository.save(categoryDO);
    }

    private BackstageCategoryDO conver(CategorysBO categorysBO) {
        BackstageCategoryDO categoryDO = new BackstageCategoryDO();
        categoryDO.setCategoryName(categorysBO.getCategoryName());
        categoryDO.setCategoryIcon(categorysBO.getCategoryIcon());
        return categoryDO;
    }

    public void isCategory(Integer categoryId) {
        Optional<BackstageCategoryDO> categoryDOOptional = backstageCategoryRepository.findById(categoryId.longValue());
        if (!categoryDOOptional.isPresent()) {
            LOGGER.error(categoryId + ":分类不存在");
            throw new SiteException(categoryId + ":分类不存在");
        }
        if (!DeletedEnum.NO_DELETED.getCode().equals(categoryDOOptional.get().getIsDeleted())) {
            LOGGER.error(categoryId + ":分类状态不正确");
            throw new SiteException(categoryId + ":分类状态不正确");
        }
    }

    public void addRank(Integer categoryId) {
        isCategory(categoryId);
        backstageCategoryRepository.addRank(categoryId.longValue());
    }

    public void subRank(Integer categoryId) {
        isCategory(categoryId);
        backstageCategoryRepository.subRank(categoryId.longValue());
    }

    public BackstageCategoryDO getCategoryById(Long id,Integer isDeleted) {
        BackstageCategoryDO categoryDO = new BackstageCategoryDO();
        categoryDO.setId(id);
        categoryDO.setIsDeleted(isDeleted);
        Example<BackstageCategoryDO> example = Example.of(categoryDO);
        Optional<BackstageCategoryDO> categoryDOOptional = backstageCategoryRepository.findOne(example);
       return categoryDOOptional.orElse(null);
    }


    /**
     * 删除分类
     *
     * @param ids 需要删除得分类id
     */
    public void deleteCategorys(Integer[] ids) {
        Map<Long, String> categoriesNameMap = getAllCategoriesNameMap();
        for (Integer id : ids) {
            //首先查询是否存在博客信息使用该分类
            List<BackstageBlogDO> blogDOS = backstageBlogService.findBlogsByCategoryId(id);
            if (Objects.nonNull(blogDOS) && blogDOS.size() > 0) {
                throw new SiteException(categoriesNameMap.get(id.longValue()) + ":该分类存在博客正在使用,不允许删除");
            }
            backstageCategoryRepository.deleteById(id.longValue());
        }
    }
}
