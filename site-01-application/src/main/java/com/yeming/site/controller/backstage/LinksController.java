package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.controller.vo.request.LinksVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.service.common.BackstageLinkService;
import com.yeming.site.service.dto.LinksBO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 友链管理
 * @date 2020/3/5 17:35
 */
@Api(value = "BlogLinksController", description = "友链管理接口")
@Controller
@RequestMapping("/backstage/links")
public class LinksController {
    private static Logger LOGGER = LoggerFactory.getLogger(LinksController.class);

    @Resource
    private BackstageLinkService backstageLinkService;

    @ApiOperation(value = "跳转<友情链接>页面", notes = "友情链接", httpMethod = "GET", tags = "友链管理API")
    @GetMapping("/manage")
    public String managePage(HttpServletRequest request) {
        LOGGER.info("进入<友情链接>页面");
        request.setAttribute("path", AllConstants.Path.LINKS);
        return "backstage/link_handle";
    }

    @ApiOperation(value = "分页查询友链信息", notes = "友情链接", httpMethod = "GET", tags = "友链管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "long", paramType = "query", required = true, defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'List<LinksBO>'}")
    })
    @GetMapping("/")
    @ResponseBody
    public ResultVO<LinksVO> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        LOGGER.info("友链分页查询:{}", JSON.toJSONString(params));
        ResultVO<LinksVO> resultVO = new ResultVO<>();
        LinksVO linksVO = new LinksVO();
        LinksBO linksBO = new LinksBO();
        try {
            if (StringUtils.isEmpty(params.get("page")) ||
                    StringUtils.isEmpty(params.get("limit"))) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            linksBO.setPage(Integer.parseInt(String.valueOf(params.get("page"))) - 1);
            linksBO.setLimit(Integer.parseInt(String.valueOf(params.get("limit"))));
            backstageLinkService.queryLinksByPage(linksBO);
            BeanUtils.copyProperties(linksBO, linksVO);
            resultVO.returnSuccessWithData(linksVO);
            LOGGER.info("友链分页查询结果:{}", JSON.toJSONString(linksVO.getResultList()));
        } catch (SiteException bize) {
            LOGGER.warn("友链分页查询失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("友链分页查询出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "获取指定的友链信息", notes = "友情链接", httpMethod = "GET", tags = "友链管理API")
    @ApiImplicitParams({@ApiImplicitParam(name = "linkId", value = "友链ID", dataType = "long", paramType = "path", required = true, defaultValue = "1")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'LinksVO'}")})
    @GetMapping("/{linkId:\\d+}")
    @ResponseBody
    public ResultVO<LinksVO> getLinkById(@NotNull(message = "请输入友链ID") @PathVariable("linkId") Long linkId) {
        LOGGER.info("获取id={}的友链信息", linkId);
        ResultVO<LinksVO> resultVO = new ResultVO<>();
        LinksVO linksVO = new LinksVO();
        LinksBO linksBO = new LinksBO();
        try {
            linksBO.setId(linkId);
            backstageLinkService.queryLinksById(linksBO);
            BeanUtils.copyProperties(linksBO, linksVO);
            resultVO.returnSuccessWithData(linksVO);
            LOGGER.info("成功获取友链信息:{}", JSON.toJSONString(linksVO));
        } catch (SiteException bize) {
            LOGGER.warn("友链信息获取失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("友链信息获取出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "新增友链信息到数据库", notes = "友情链接", httpMethod = "POST", tags = "友链管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO save(@ApiParam(name = "友链新增对象", value = "传入json格式", required = true)
                         @Validated @RequestBody LinksVO linksVO) {
        LOGGER.info("新增友链信息:{}", JSON.toJSONString(linksVO));
        ResultVO resultVO = new ResultVO();
        LinksBO linksBO = new LinksBO();
        try {
            BeanUtils.copyProperties(linksVO, linksBO);
            backstageLinkService.saveOrUpdateBlog(linksBO);
            resultVO.returnSuccess();
            LOGGER.info("友链信息新增成功:{}", JSON.toJSONString(linksVO));
        } catch (SiteException bize) {
            LOGGER.warn("友链信息新增失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("友链信息新增出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "更新友链信息到数据库", notes = "友情链接", httpMethod = "POST", tags = "友链管理API")
    @ApiImplicitParams({@ApiImplicitParam(name = "linkId", value = "友链ID", dataType = "long", paramType = "path", required = true, defaultValue = "1")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/{linkId:\\d+}")
    @ResponseBody
    public ResultVO update(@ApiParam(name = "友链修改对象", value = "传入json格式", required = true)
                           @Validated @RequestBody LinksVO linksVO,
                           @NotNull(message = "请输入友链ID") @PathVariable("linkId") Long linkId) {
        LOGGER.info("更新友链id={}信息为:{}", linkId, JSON.toJSONString(linksVO));
        ResultVO resultVO = new ResultVO();
        LinksBO linksBO = new LinksBO();
        try {
            BeanUtils.copyProperties(linksVO, linksBO);
            linksBO.setId(linkId);
            backstageLinkService.saveOrUpdateBlog(linksBO);
            resultVO.returnSuccess();
            LOGGER.info("友链信息更新成功:{}", JSON.toJSONString(linksBO));
        } catch (SiteException bize) {
            LOGGER.warn("友链信息更新失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("友链信息更新出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }


    @ApiOperation(value = "删除友链信息", notes = "友情链接", httpMethod = "DELETE", tags = "友链管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @DeleteMapping("/")
    @ResponseBody
    public ResultVO delete(@ApiParam(name = "友链ID", value = "请求url匹配", required = true, type = "array")
            @NotNull(message = "请选择需要删除友链信息id") @RequestBody Integer[] ids) {
        LOGGER.info("删除友链信息，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backstageLinkService.deleteLinks(ids);
            resultVO.returnSuccess();
            LOGGER.info("ids={}所有友链信息全部删除成功", JSON.toJSONString(ids));
        } catch (SiteException bize) {
            LOGGER.warn("友链信息删除失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("友链信息删除出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

}
