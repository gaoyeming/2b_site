package com.yeming.site.interceptor;

import com.yeming.site.dao.entity.BackstageUserDO;
import com.yeming.site.service.common.BackstageUserService;
import com.yeming.site.util.CommonUtils;
import com.yeming.site.util.IPUtils;
import com.yeming.site.util.UuidUtils;
import com.yeming.site.util.constant.AllConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Resource
    private BackstageUserService backstageUserService;

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
     *
     * @param request  请求
     * @param response 响应
     * @param o        对象
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPUtils.getIpAddr(request));
        //请求拦截处理
        BackstageUserDO user = (BackstageUserDO) CommonUtils.getLoginUser(request);
        //session为空则判断cookie是否存在
        if (Objects.isNull(user)) {
            String loginUser = CommonUtils.getCookieUid(request);
            if (StringUtils.isNotBlank(loginUser)) {
                user = backstageUserService.findOne(loginUser);
                request.getSession().setAttribute(AllConstants.Web.LOGIN_SESSION_KEY, user);
            }
        }
        if (uri.startsWith("/backstage") &&
                Objects.isNull(user) &&
                !uri.startsWith("/backstage/login")) {
            request.getSession().setAttribute("errorMsg", "请重新登陆");
            response.sendRedirect(request.getContextPath() + "/backstage/login");
            return false;
        }

        request.getSession().removeAttribute("errorMsg");
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
     *
     * @param request      请求
     * @param response     响应
     * @param o            对象
     * @param modelAndView ModelAndView
     * @throws Exception Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //一些工具类和公共方法
        request.setAttribute("commons", new UuidUtils());
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     *
     * @param request  请求
     * @param response 响应
     * @param o        对象
     * @param e        Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

    }
}
