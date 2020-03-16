package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.controller.vo.request.UserVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.dao.entity.BackstageUserDO;
import com.yeming.site.service.common.BackstageUserService;
import com.yeming.site.service.dto.UserBO;
import com.yeming.site.util.CommonUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 用户控制层
 * @date 2020/3/5 21:12
 */
@Api(value = "UserController", description = "后台登陆用户管理接口")
@Controller
@RequestMapping("/backstage/profile")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private BackstageUserService backstageUserService;

    @ApiOperation(value = "跳转<修改密码>页面", notes = "修改密码", httpMethod = "GET", tags = "后台登陆用户管理API")
    @GetMapping("/manage")
    public String profile(HttpServletRequest request) {
        LOGGER.info("进入<修改密码>页面");
        BackstageUserDO user = (BackstageUserDO) CommonUtils.getLoginUser(request);
        if (Objects.isNull(user)) {
            LOGGER.warn("当前登陆用户session已失效");
            return "backstage/login";
        }
        BackstageUserDO adminUser = backstageUserService.findOne(user.getLoginUser());
        if (Objects.isNull(adminUser)) {
            LOGGER.warn("当前登陆用户:{}不存在", JSON.toJSONString(user));
            return "backstage/login";
        }
        request.setAttribute("path", AllConstants.Path.PROFILE);
        request.setAttribute("loginUserName", user.getLoginUser());
        request.setAttribute("nickName", user.getNickName());
        return "backstage/profile";
    }

    @ApiOperation(value = "更新后台登陆用户信息到数据库", notes = "修改密码", httpMethod = "POST", tags = "后台登陆用户管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO updateUser(HttpServletRequest request,
                               @ApiParam(name = "后台登陆用户修改对象", value = "传入json格式", required = true)
                               @Validated @RequestBody UserVO userVO) {
        LOGGER.info("更新用户信息:{}", JSON.toJSONString(userVO));
        ResultVO resultVO = new ResultVO();
        UserBO userBO = new UserBO();
        try {
            //获取当前登陆用户
            BackstageUserDO loginUser = (BackstageUserDO) CommonUtils.getLoginUser(request);
            BeanUtils.copyProperties(userVO, userBO);
            backstageUserService.updateUser(userBO, loginUser);
            resultVO.returnSuccess();
            LOGGER.info("用户信息更新成功:{}", JSON.toJSONString(userVO));
        } catch (SiteException bize) {
            LOGGER.warn("用户信息更新失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("用户信息更新出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
