package com.yeming.site.controller.backstage;

import com.yeming.site.dao.entity.BackstageUserDO;
import com.yeming.site.service.common.*;
import com.yeming.site.util.CipherUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 后台管理登陆相关接口
 * @date 2020/2/24 16:00
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/backstage")
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private BackstageUserService backstageUserService;

    @Resource
    private BackstageCategoryService backstageCategoryService;

    @Resource
    private BackstageBlogCommentService backstageBlogCommentService;

    @Resource
    private BackstageLinkService backstageLinkService;

    @Resource
    private BackstageBlogService backstageBlogService;

    @Resource
    private BackstageTagService backstageTagService;

    @GetMapping(value = "/login")
    public String login(HttpServletRequest request) {
        request.getSession().removeAttribute(AllConstants.Web.LOGIN_SESSION_KEY);
        request.getSession().removeAttribute("nickName");
        request.getSession().removeAttribute("errorMsg");
        return "backstage/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "backstage/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "backstage/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "backstage/login";
        }
        try {
            password = CipherUtils.enAes(password, AllConstants.Web.AES_SALT);
            LOGGER.info("当前登录用户userName={}、password={}", userName, password);
        } catch (Exception e) {
            LOGGER.error("密码加密出现异常:", e);
        }
        BackstageUserDO loginUser = backstageUserService.findOne(userName);
        if (Objects.nonNull(loginUser)) {
            if (password.equals(loginUser.getLoginPassword())) {
                session.setAttribute("nickName", loginUser.getNickName());
                session.setAttribute(AllConstants.Web.LOGIN_SESSION_KEY, loginUser);
                return "redirect:/backstage/index";
            } else {
                session.setAttribute("errorMsg", "密码错误");
                return "backstage/login";
            }

        } else {
            session.setAttribute("errorMsg", "登陆用户不存在");
            return "backstage/login";
        }
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", AllConstants.Path.INDEX);
        //分类数量
        request.setAttribute("categoryCount", backstageCategoryService.getTotalCategories(DeletedEnum.NO_DELETED.getCode()));
        //标签总量
        request.setAttribute("tagCount", backstageTagService.getTotalTags(DeletedEnum.NO_DELETED.getCode()));
        //收到评论数
        request.setAttribute("commentCount", backstageBlogCommentService.getTotalComments(DeletedEnum.NO_DELETED.getCode()));
        //总文章数
        request.setAttribute("blogCount", backstageBlogService.getTotalBlogs(DeletedEnum.NO_DELETED.getCode()));
        //友情链接
        request.setAttribute("linkCount", backstageLinkService.getTotalLinks(DeletedEnum.NO_DELETED.getCode()));
        return "backstage/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(AllConstants.Web.LOGIN_SESSION_KEY);
        request.getSession().removeAttribute("nickName");
        request.getSession().removeAttribute("errorMsg");
        return "backstage/login";
    }

}
