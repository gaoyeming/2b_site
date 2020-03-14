package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.controller.vo.request.CategorysVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.service.common.BackstageCategoryService;
import com.yeming.site.service.dto.CategorysBO;
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
 * @Description: 分类管理
 * @date 2020/3/5 9:42
 */
@Api(value = "CategorysController", description = "分类管理接口")
@Controller
@RequestMapping("/backstage/categories")
public class CategorysController {

    private static Logger LOGGER = LoggerFactory.getLogger(CategorysController.class);

    @Resource
    private BackstageCategoryService backstageCategoryService;

    @ApiOperation(value = "跳转<分类管理>页面", notes = "分类管理", httpMethod = "GET", tags = "分类管理API")
    @GetMapping("/manage")
    public String managePage(HttpServletRequest request) {
        LOGGER.info("进入<分类管理>页面");
        request.setAttribute("path", AllConstants.Path.CATEGORIES);
        return "backstage/category_handle";
    }

    @ApiOperation(value = "分页查询分类信息", notes = "分类管理", httpMethod = "GET", tags = "分类管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "long", paramType = "query", required = true, defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'List<CategorysBO>'}")
    })
    @GetMapping("/")
    @ResponseBody
    public ResultVO<CategorysVO> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        LOGGER.info("分类分页查询:{}", JSON.toJSONString(params));
        ResultVO<CategorysVO> resultVO = new ResultVO<>();
        CategorysVO categorysVO = new CategorysVO();
        CategorysBO categorysBO = new CategorysBO();
        try {
            if (StringUtils.isEmpty(params.get("page")) ||
                    StringUtils.isEmpty(params.get("limit"))) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            categorysBO.setPage(Integer.parseInt(String.valueOf(params.get("page"))) - 1);
            categorysBO.setLimit(Integer.parseInt(String.valueOf(params.get("limit"))));
            backstageCategoryService.queryBlogByPage(categorysBO);
            BeanUtils.copyProperties(categorysBO, categorysVO);
            resultVO.returnSuccessWithData(categorysVO);
            LOGGER.info("分类分页查询结果:{}", JSON.toJSONString(categorysVO.getResultList()));
        } catch (SiteException bize) {
            LOGGER.warn("分类分页查询失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("分类分页查询出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "新增分类信息到数据库", notes = "分类管理", httpMethod = "POST", tags = "分类管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO save(@ApiParam(name = "分类新增对象", value = "传入json格式", required = true)
                         @Validated @RequestBody CategorysVO categorysVO) {
        LOGGER.info("新增分类信息:{}", JSON.toJSONString(categorysVO));
        ResultVO resultVO = new ResultVO();
        CategorysBO categorysBO = new CategorysBO();
        try {
            BeanUtils.copyProperties(categorysVO, categorysBO);
            backstageCategoryService.saveOrUpdateBlog(categorysBO);
            resultVO.returnSuccess();
            LOGGER.info("分类信息新增成功:{}", JSON.toJSONString(categorysVO));
        } catch (SiteException bize) {
            LOGGER.warn("分类信息新增失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("分类信息新增出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "更新分类信息到数据库", notes = "分类管理", httpMethod = "POST", tags = "分类管理API")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "分类ID", dataType = "long", paramType = "path", required = true, defaultValue = "1")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/{categoryId:\\d+}")
    @ResponseBody
    public ResultVO update(@ApiParam(name = "分类修改对象", value = "传入json格式", required = true)
                           @Validated @RequestBody CategorysVO categorysVO,
                           @NotNull(message = "请输入分类ID") @PathVariable("categoryId") Long categoryId) {
        LOGGER.info("更新分类id={}信息为:{}", categoryId, JSON.toJSONString(categorysVO));
        ResultVO resultVO = new ResultVO();
        CategorysBO categorysBO = new CategorysBO();
        try {
            BeanUtils.copyProperties(categorysVO, categorysBO);
            categorysBO.setId(categoryId);
            backstageCategoryService.saveOrUpdateBlog(categorysBO);
            resultVO.returnSuccess();
            LOGGER.info("分类信息更新成功:{}", JSON.toJSONString(categorysBO));
        } catch (SiteException bize) {
            LOGGER.warn("分类信息更新失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("分类信息更新出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "删除分类信息", notes = "分类管理", httpMethod = "DELETE", tags = "分类管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @DeleteMapping("/")
    @ResponseBody
    public ResultVO delete(@ApiParam(name = "分类ID", value = "请求url匹配", required = true, type = "array")
                           @NotNull(message = "请选择需要删除分类信息id") @RequestBody Integer[] ids) {
        LOGGER.info("删除分类信息，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backstageCategoryService.deleteCategorys(ids);
            resultVO.returnSuccess();
            LOGGER.info("ids={}所有分类信息全部删除成功", JSON.toJSONString(ids));
        } catch (SiteException bize) {
            LOGGER.warn("分类信息删除失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("分类信息删除出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
