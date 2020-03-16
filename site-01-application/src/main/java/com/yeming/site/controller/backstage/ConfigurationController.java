package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.biz.BacksatgeBiz;
import com.yeming.site.controller.vo.request.SysConfigVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yeming.gao
 * @Description: 系统配置
 * @date 2020/3/5 20:52
 */
@Api(value = "ConfigurationController", description = "系统配置管理接口")
@Controller
@RequestMapping("/backstage/configurations")
public class ConfigurationController {

    private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);

    @Resource
    private BacksatgeBiz backsatgeBiz;

    @ApiOperation(value = "跳转<系统配置>页面", notes = "系统配置", httpMethod = "GET", tags = "系统配置管理API")
    @GetMapping("/manage")
    public String list(HttpServletRequest request) {
        LOGGER.info("进入<系统配置>页面");
        request.setAttribute("path", AllConstants.Path.CONFIGURATIONS);
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "backstage/configuration";
    }

    @ApiOperation(value = "更新系统配置信息到数据库", notes = "系统配置", httpMethod = "POST", tags = "系统配置管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO website(@ApiParam(name = "系统配置修改对象", value = "传入json格式", required = true)
                            @Validated @RequestBody SysConfigVO sysConfigVO) {
        LOGGER.info("更新系统配置信息:{}", JSON.toJSONString(sysConfigVO));
        ResultVO resultVO = new ResultVO();
        try {
            backsatgeBiz.updateConfig(JSON.toJSONString(sysConfigVO));
            resultVO.returnSuccess();
            LOGGER.info("系统配置信息更新成功:{}", JSON.toJSONString(sysConfigVO));
        } catch (SiteException bize) {
            LOGGER.warn("系统配置信息更新失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("系统配置信息更新出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
