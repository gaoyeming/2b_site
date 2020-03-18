package com.yeming.site.service.common;

import com.yeming.site.dao.entity.LeaveMessageDO;
import com.yeming.site.dao.repository.LeaveMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yeming.gao
 * @Description: 博客标签表公共服务层
 * @date 2019/11/6 14:05
 */
@Service
public class LeaveMessageService {

    private static Logger LOGGER = LoggerFactory.getLogger(LeaveMessageService.class);

    @Resource
    private LeaveMessageRepository leaveMessageRepository;


    public void saveMessage(LeaveMessageDO leaveMessageDO) {
        leaveMessageRepository.save(leaveMessageDO);
    }
}
