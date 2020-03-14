package com.yeming.site.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yeming.gao
 * @Description: 自定义配置参数内容
 * @date 2020/3/13 17:21
 */
@Component
@Setter
@Getter
public class PropertiesConfig {

    @Value("${customize.http.static.url}")
    private String httpStaticUrl;

    @Value("${customize.file.upload.dic}")
    private String fileUploadDic;

}
