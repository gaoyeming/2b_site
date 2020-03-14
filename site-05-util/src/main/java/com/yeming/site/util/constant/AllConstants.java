package com.yeming.site.util.constant;

/**
 * @author yeming.gao
 * @Description: 常量类
 * @date 2019/4/28 10:31
 */
public interface AllConstants {

    interface Common {
        String LOCALHOST_IP = "127.0.0.1";
        String UNKNOWN = "unknown";
        String SPLIT_COMMA = ",";
        String SPLIT_POINT = ".";
        String SPLIT_KIND = "|";
        String SPLIT_BIAS = "/";
        String LOCALHOST_ADRESS = "0:0:0:0:0:0:0:1";
        String OPTIONS = "OPTIONS";
        Integer ONE = 1;
        Integer NEGATIVE_ONE = -1;
        Integer ZERO = 0;
        String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
        String DATE_FORMAT_YYYYMMDD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
        String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
        String PREFIX_MD = "MD";
        String PREFIX_FM = "FM";
    }

    interface Web {
        String LOGIN_SESSION_KEY = "login_user";
        String USER_IN_COOKIE = "S_L_ID";
        /**
         * aes加密加盐
         */
        String AES_SALT = "0123456789abcdef";
    }

    interface Path {
        String ADD = "add";
        String BLOGS = "blogs";
        String INDEX = "index";
        String EDIT = "edit";
        String COMMENTS = "comments";
        String CATEGORIES = "categories";
        String TAGS = "tags";
        String LINKS = "links";
        String CONFIGURATIONS = "configurations";
        String PROFILE = "profile";
    }

    interface SysConfig {
        String WEBSITE_NAME = "websiteName";
        String WEBSITE_DESCRIPTION = "websiteDescription";
        String WEBSITE_LOGO = "websiteLogo";
        String WEBSITE_ICON = "websiteIcon";
        String YOUR_AVATAR = "yourAvatar";
        String YOUR_EMAIL = "yourEmail";
        String YOUR_NAME = "yourName";
        String FOOTER_ABOUT = "footerAbout";
        String FOOTER_ICP = "footerICP";
        String FOOTER_COPYRIGHT = "footerCopyRight";
        String FOOTER_POWEREDBY = "footerPoweredBy";
        String FOOTER_POWEREDBYURL = "footerPoweredByURL";
    }


}
