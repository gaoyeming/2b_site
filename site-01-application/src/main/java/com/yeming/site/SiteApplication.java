package com.yeming.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration(exclude = MultipartAutoConfiguration.class)
@EnableCaching
public class SiteApplication {

    public static void main(String[] args) {
        //调用SpringApplication启动一个Spring Boot应用
        SpringApplication.run(SiteApplication.class, args);
    }

}
