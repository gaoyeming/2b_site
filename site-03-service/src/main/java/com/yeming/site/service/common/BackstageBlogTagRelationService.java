package com.yeming.site.service.common;

import com.yeming.site.dao.entity.BackstageBlogTagRelationDO;
import com.yeming.site.dao.repository.BackstageBlogTagRelationRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 博客标签关联表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class BackstageBlogTagRelationService {

    @Resource
    private BackstageTagService backstageTagService;
    @Resource
    private BackstageBlogTagRelationRepository backstageBlogTagRelationRepository;

    public void saveRelation(Integer blogId, String[] tagIds) {
        backstageTagService.isTags(tagIds);
        for (String tagId : tagIds) {
            BackstageBlogTagRelationDO relationDO = new BackstageBlogTagRelationDO();
            Integer id = Integer.parseInt(tagId);
            relationDO.setBlogId(blogId);
            relationDO.setTagId(id);
            backstageBlogTagRelationRepository.save(relationDO);
        }
    }

    public void updateRelation(Integer blogId, String[] tagIds) {
        backstageTagService.isTags(tagIds);
        List<BackstageBlogTagRelationDO> relationDOList = findRelationByBlogId(blogId);
        if (Objects.isNull(relationDOList) || relationDOList.size() == 0) {
            saveRelation(blogId, tagIds);
        } else {
            deleteRelation(blogId);
            saveRelation(blogId, tagIds);
        }
    }

    public List<BackstageBlogTagRelationDO> findRelationByBlogId(Integer blogId) {
        BackstageBlogTagRelationDO relationDO = new BackstageBlogTagRelationDO();
        relationDO.setBlogId(blogId);
        Example<BackstageBlogTagRelationDO> example = Example.of(relationDO);
        return backstageBlogTagRelationRepository.findAll(example);
    }

    public List<BackstageBlogTagRelationDO> findRelationByTagId(Integer tagId) {
        BackstageBlogTagRelationDO relationDO = new BackstageBlogTagRelationDO();
        relationDO.setTagId(tagId);
        Example<BackstageBlogTagRelationDO> example = Example.of(relationDO);
        return backstageBlogTagRelationRepository.findAll(example);
    }

    public void deleteRelation(Integer blogId) {
        backstageBlogTagRelationRepository.deleteByBlogId(blogId);
        backstageBlogTagRelationRepository.flush();
    }

}
