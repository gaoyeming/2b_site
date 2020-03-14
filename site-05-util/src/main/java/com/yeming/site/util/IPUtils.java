package com.yeming.site.util;

import com.yeming.site.util.constant.AllConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yeming.gao
 * @Description: 获取访问的ip地址工具类
 * @date 2019/5/14 10:01
 */
public class IPUtils {
    private IPUtils() {
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAdress = request.getHeader("x-forwarded-for");
        if (ipAdress != null && ipAdress.length() != 0 && !AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ipAdress.contains(AllConstants.Common.SPLIT_COMMA)) {
                ipAdress = ipAdress.split(",")[0];
            }
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("X-Real-IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || AllConstants.Common.UNKNOWN.equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getRemoteAddr();
        }
        if (AllConstants.Common.LOCALHOST_ADRESS.equals(ipAdress)) {
            ipAdress = AllConstants.Common.LOCALHOST_IP;
        }
        return ipAdress;
    }

}
