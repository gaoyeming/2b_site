package com.yeming.site.controller.resume;

import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.biz.BacksatgeBiz;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.dao.entity.LeaveMessageDO;
import com.yeming.site.service.common.LeaveMessageService;
import com.yeming.site.util.exception.SiteException;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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

    @Resource
    private LeaveMessageService leaveMessageService;

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

    @PostMapping("/leaveMessage")
    @ResponseBody
    @SysOperationLog("个人简介留言")
    public ResultVO reply(@NotEmpty(message = "请输入姓名") @Length(max = 50, message = "姓名长度过长") @RequestParam(name = "name") String name,
                          @NotEmpty(message = "请输入邮箱") @Email(message = "邮箱格式不正确") @Length(max = 100, message = "邮箱长度过长") @RequestParam(name = "email") String email,
                          @NotEmpty(message = "请输入留言") @Length(max = 200, message = "留言信息长度过长") @RequestParam(name = "message") String message) {
        LOGGER.info("收到{}的留言:邮箱为{}留言信息为:{}", name, email, message);
        ResultVO resultVO = new ResultVO();
        try {
            LeaveMessageDO leaveMessageDO = new LeaveMessageDO();
            leaveMessageDO.setName(name);
            leaveMessageDO.setEmail(email);
            leaveMessageDO.setMessage(message);
            leaveMessageService.saveMessage(leaveMessageDO);
            resultVO.returnSuccess();
            LOGGER.info("留言成功");
        } catch (SiteException bize) {
            LOGGER.warn("留言失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("留言出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
