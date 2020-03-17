package com.yeming.site.controller.resume;

import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.biz.BacksatgeBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yeming.gao
 * @Description: 个人简介页面
 * @date 2020/3/17 15:53
 */
@Controller
public class ResumeController {

    private static Logger LOGGER = LoggerFactory.getLogger(ResumeController.class);

    @Resource
    private BacksatgeBiz backsatgeBiz;

    /**
     * 首页
     *
     * @return String 首页
     */
    @GetMapping("/about")
    @SysOperationLog("访问我的个人简介")
    public String index(HttpServletRequest request) {
        LOGGER.info("进入关于博主页面");
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "resume/resume";
    }
}
