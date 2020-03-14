package com.yeming.site.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yeming.gao
 * @Description: 用来监控web应用是否启动成功，和监控DB连接是否成功
 * @date 2019/12/05 16:15
 */

@ApiIgnore
@RestController
public class MonitorController {

    @GetMapping("/monitorWeb")
    public String monitorWeb() {
        return "WEB_OK";
    }

}
