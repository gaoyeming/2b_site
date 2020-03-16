package com.yeming.site.config;

import com.yeming.site.interceptor.BaseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
/**
 * @author yeming.gao
 * @Description: webnvc配置类
 * @date 2020/2/24 16:00
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Resource
    private PropertiesConfig propertiesConfig;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/backstage为前缀的url路径
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/backstage/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + propertiesConfig.getFileUploadDic());
    }
}
