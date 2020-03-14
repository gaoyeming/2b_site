package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageLinkDO;
import com.yeming.site.dao.repository.BackstageLinkRepository;
import com.yeming.site.service.dto.LinksBO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yeming.gao
 * @Description: 博客友链表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageLinkService {

    private static Logger LOGGER = LoggerFactory.getLogger(BackstageCategoryService.class);

    @Resource
    private BackstageLinkRepository backstageLinkRepository;


    public Integer getTotalLinks(Integer isDeleted) {
        BackstageLinkDO blogLinkDO = new BackstageLinkDO();
        blogLinkDO.setIsDeleted(isDeleted);
        Example<BackstageLinkDO> example = Example.of(blogLinkDO);
        long count = backstageLinkRepository.count(example);
        LOGGER.info("获取数据库中友链的数量,共{}条", count);
        return (int) count;
    }

    public void queryLinksById(LinksBO linksBO) {
        Optional<BackstageLinkDO> linkDOOptional = backstageLinkRepository.findById(linksBO.getId());
        if (linkDOOptional.isPresent()) {
            linksBO.setLinkType(linkDOOptional.get().getLinkType());
            linksBO.setLinkName(linkDOOptional.get().getLinkName());
            linksBO.setLinkUrl(linkDOOptional.get().getLinkUrl());
            linksBO.setLinkDescription(linkDOOptional.get().getLinkDescription());
            linksBO.setLinkRank(linkDOOptional.get().getLinkRank());
        }
    }

    /**
     * 分页查询友链
     *
     * @param linksBO 参数对象
     */
    public void queryLinksByPage(LinksBO linksBO) {
        Sort sort = Sort.by(Sort.Order.desc("linkRank"));
        Pageable pageable = PageRequest.of(linksBO.getPage(), linksBO.getLimit(), sort);
        BackstageLinkDO linkDO = new BackstageLinkDO();
        linkDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageLinkDO> example = Example.of(linkDO);
        Page<BackstageLinkDO> page = backstageLinkRepository.findAll(example, pageable);
        List<BackstageLinkDO> linkDOS = page.getContent();
        linksBO.setTotalPage(page.getTotalPages());
        linksBO.setTotalCount((int) page.getTotalElements());
        //整合结果集
        List<LinksBO> resultList = new ArrayList<>();
        linkDOS.forEach(backstageLinkDO -> {
            LinksBO bo = new LinksBO();
            bo.setLinkId(backstageLinkDO.getId().intValue());
            bo.setLinkName(backstageLinkDO.getLinkName());
            bo.setLinkUrl(backstageLinkDO.getLinkUrl());
            bo.setLinkDescription(backstageLinkDO.getLinkDescription());
            bo.setLinkRank(backstageLinkDO.getLinkRank());
            bo.setAddTime(DateUtils.formatDateToStr(backstageLinkDO.getCreateTime(), AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS));
            resultList.add(bo);
        });
        linksBO.setResultList(resultList);
    }

    /**
     * 新增或者更新友链信息
     *
     * @param linksBO 参数对象
     */
    public void saveOrUpdateBlog(LinksBO linksBO) {
        BackstageLinkDO linkDO = conver(linksBO);
        linkDO.setId(linksBO.getId());
        if (Objects.isNull(linksBO.getId())) {
            //表示为新增，需要判断标签名称是否重复
            BackstageLinkDO blogLinkDO = new BackstageLinkDO();
            blogLinkDO.setLinkUrl(linksBO.getLinkUrl());
            blogLinkDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
            Example<BackstageLinkDO> example = Example.of(linkDO);
            Optional<BackstageLinkDO> linkDOOptional = backstageLinkRepository.findOne(example);
            if (linkDOOptional.isPresent()) {
                throw new SiteException("当前网站已存在");
            }
        }
        backstageLinkRepository.save(linkDO);
    }

    /**
     * 删除友链
     *
     * @param ids 需要删除得友链id
     */
    public void deleteLinks(Integer[] ids) {
        for (Integer id : ids) {
            backstageLinkRepository.deleteById(id.longValue());
        }
    }

    private BackstageLinkDO conver(LinksBO linksBO) {
        BackstageLinkDO linkDO = new BackstageLinkDO();
        linkDO.setLinkType(linksBO.getLinkType());
        linkDO.setLinkName(linksBO.getLinkName());
        linkDO.setLinkUrl(linksBO.getLinkUrl());
        linkDO.setLinkDescription(linksBO.getLinkDescription());
        linkDO.setLinkRank(linksBO.getLinkRank());
        return linkDO;
    }

    public Map<Integer, List<BackstageLinkDO>> getAllLinks() {
        Sort sort = Sort.by(Sort.Order.desc("linkRank"));
        BackstageLinkDO linkDO = new BackstageLinkDO();
        linkDO.setIsDeleted(DeletedEnum.NO_DELETED.getCode());
        Example<BackstageLinkDO> example = Example.of(linkDO);
        List<BackstageLinkDO> linkDOS = backstageLinkRepository.findAll(example, sort);
        if (!CollectionUtils.isEmpty(linkDOS)) {
            //根据type进行分组
            return linkDOS.stream().collect(Collectors.groupingBy(BackstageLinkDO::getLinkType));
        }
        return null;
    }
}
