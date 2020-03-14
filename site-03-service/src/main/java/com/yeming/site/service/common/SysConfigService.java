package com.yeming.site.service.common;

import com.yeming.site.dao.entity.SysConfigDO;
import com.yeming.site.dao.repository.SysConfigRepository;
import com.yeming.site.service.dto.SysConfigBO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.exception.SiteException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author yeming.gao
 * @Description: 系统参数配置公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class SysConfigService {

    private static Logger LOGGER = LoggerFactory.getLogger(SysConfigService.class);


    @Resource
    private SysConfigRepository sysConfigRepository;


    public List<SysConfigDO> getAll() {
        return sysConfigRepository.findAll();
    }

    public void updateConfig(List<SysConfigBO> sysConfigBOList) {
        StringBuilder errorStr = new StringBuilder();
        sysConfigBOList.forEach(sysConfigBO -> {
            SysConfigDO sysConfigDO = new SysConfigDO();
            sysConfigDO.setConfigName(sysConfigBO.getConfigName());
            Example<SysConfigDO> example = Example.of(sysConfigDO);
            Optional<SysConfigDO> exampleResult = sysConfigRepository.findOne(example);
            if (exampleResult.isPresent()) {
                sysConfigDO.setConfigValue(sysConfigBO.getConfigValue());
                sysConfigDO.setId(exampleResult.get().getId());
                sysConfigRepository.save(sysConfigDO);
                LOGGER.info("{}更新成功", sysConfigDO.getConfigName());
            } else {
                errorStr.append(sysConfigBO.getConfigName()).append(AllConstants.Common.SPLIT_COMMA);
            }
        });
        if (StringUtils.isNotEmpty(errorStr.toString())) {
            errorStr.deleteCharAt(errorStr.length() - 1);
            LOGGER.error(errorStr.toString() + "配置不存在,无需更新");
            throw new SiteException(errorStr.toString() + "配置不存在,无需更新");
        }
    }
}
