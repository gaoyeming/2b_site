package com.yeming.site.util;


import com.yeming.site.util.constant.AllConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;

/**
 * @author yeming.gao
 * @Description: 公共工具类
 * @date 2019/5/14 10:01
 */
public class CommonUtils {
    private CommonUtils() {
    }

    public static URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }
        return effectiveURI;
    }
    /**
     * 在session中获取当前登录用户对象
     *
     * @param request 请求
     * @return Object
     */
    public static Object getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return session.getAttribute(AllConstants.Web.LOGIN_SESSION_KEY);
    }


    /**
     * 获取cookie中的用户id
     *
     * @param request 请求
     * @return Integer
     */
    public static String getCookieUid(HttpServletRequest request) {
        if (null != request) {
            Cookie cookie = cookieRaw(AllConstants.Web.USER_IN_COOKIE, request);
            if (cookie != null && cookie.getValue() != null) {
                try {
                    return CipherUtils.deAes(cookie.getValue(), AllConstants.Web.AES_SALT);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 判断字符串是否为数字和有正确的值
     *
     * @param str 判断对象
     * @return boolean
     */
    public static boolean isNumber(String str) {
        return null != str && 0 != str.trim().length() && str.matches("\\d*");
    }

    /**
     * 判断字符串是否为数字和有正确的值
     *
     * @param str 判断对象
     * @return boolean
     */
    public static boolean includes(String[] str,int i) {
        for (String s : str) {
            if(s.equals(String.valueOf(i))){
                return true;
            }
        }
        return false;
    }


    /**
     * 从cookies中获取指定cookie
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    private static Cookie cookieRaw(String name, HttpServletRequest request) {
        Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
