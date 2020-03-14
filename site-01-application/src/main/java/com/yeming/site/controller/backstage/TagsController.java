package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.controller.vo.request.TagsVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.service.common.BackstageTagService;
import com.yeming.site.service.dto.TagsBO;
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
 * @Description: 标签管理
 * @date 2020/3/5 17:35
 */
@Api(value = "BlogTagsController", description = "标签管理接口")
@Controller
@RequestMapping("/backstage/tags")
public class TagsController {

    private static Logger LOGGER = LoggerFactory.getLogger(CategorysController.class);

    @Resource
    private BackstageTagService backstageTagService;

    @ApiOperation(value = "跳转<标签管理>页面", notes = "标签管理", httpMethod = "GET", tags = "标签管理API")
    @GetMapping("/manage")
    public String managePage(HttpServletRequest request) {
        LOGGER.info("进入<标签管理>页面");
        request.setAttribute("path", AllConstants.Path.TAGS);
        return "backstage/tag_handle";
    }

    @ApiOperation(value = "分页查询标签信息", notes = "标签管理", httpMethod = "GET", tags = "标签管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "long", paramType = "query", required = true, defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'List<TagsBO>'}")
    })
    @GetMapping("/")
    @ResponseBody
    public ResultVO<TagsVO> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        LOGGER.info("标签分页查询:{}", JSON.toJSONString(params));
        ResultVO<TagsVO> resultVO = new ResultVO<>();
        TagsVO tagsVO = new TagsVO();
        TagsBO tagsBO = new TagsBO();
        try {
            if (StringUtils.isEmpty(params.get("page")) ||
                    StringUtils.isEmpty(params.get("limit"))) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            tagsBO.setPage(Integer.parseInt(String.valueOf(params.get("page"))) - 1);
            tagsBO.setLimit(Integer.parseInt(String.valueOf(params.get("limit"))));
            backstageTagService.queryTagsByPage(tagsBO);
            BeanUtils.copyProperties(tagsBO, tagsVO);
            resultVO.returnSuccessWithData(tagsVO);
            LOGGER.info("标签分页查询结果:{}", JSON.toJSONString(tagsVO.getResultList()));
        } catch (SiteException bize) {
            LOGGER.warn("标签分页查询失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("标签分页查询出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "新增标签信息到数据库", notes = "标签管理", httpMethod = "POST", tags = "标签管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO save(@ApiParam(name = "标签新增对象", value = "传入json格式", required = true)
                         @Validated @RequestBody TagsVO tagsVO) {
        LOGGER.info("新增标签信息:{}", JSON.toJSONString(tagsVO));
        ResultVO resultVO = new ResultVO();
        TagsBO tagsBO = new TagsBO();
        try {
            BeanUtils.copyProperties(tagsVO, tagsBO);
            backstageTagService.saveOrUpdateBlog(tagsBO);
            resultVO.returnSuccess();
            LOGGER.info("标签信息新增成功:{}", JSON.toJSONString(tagsVO));
        } catch (SiteException bize) {
            LOGGER.warn("标签信息新增失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("标签信息新增出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "删除标签信息", notes = "标签管理", httpMethod = "DELETE", tags = "标签管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @DeleteMapping("/")
    @ResponseBody
    public ResultVO delete(@ApiParam(name = "标签ID", value = "请求url匹配", required = true, type = "array")
                           @NotNull(message = "请选择需要删除标签信息id") @RequestBody Integer[] ids) {
        LOGGER.info("删除标签信息，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backstageTagService.deleteTags(ids);
            resultVO.returnSuccess();
            LOGGER.info("ids={}所有标签信息全部删除成功", JSON.toJSONString(ids));
        } catch (SiteException bize) {
            LOGGER.warn("标签信息删除失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("标签信息删除出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

}
