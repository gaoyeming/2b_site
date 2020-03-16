package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.biz.BacksatgeBiz;
import com.yeming.site.controller.vo.request.BlogsVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.service.common.BackstageCategoryService;
import com.yeming.site.service.common.BackstageBlogService;
import com.yeming.site.service.common.BackstageTagService;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.DeletedEnum;
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
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 博客相关接口
 * @date 2020/2/24 16:00
 */
@Api(value = "BlogsController", description = "博客管理接口")
@Controller
@RequestMapping("/backstage/blogs")
public class BlogsController {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogsController.class);

    @Resource
    private BacksatgeBiz backsatgeBiz;
    @Resource
    private BackstageCategoryService backstageCategoryService;

    @Resource
    private BackstageBlogService backstageBlogService;

    @Resource
    private BackstageTagService backstageTagService;

    @ApiOperation(value = "跳转<发布博客>页面发布", notes = "发布博客", httpMethod = "GET", tags = "博客管理API")
    @GetMapping("/release")
    public String releasePage(HttpServletRequest request) {
        LOGGER.info("进入<发布博客>页面");
        request.setAttribute("path", AllConstants.Path.ADD);
        request.setAttribute("categories", backstageCategoryService.getAllCategories(DeletedEnum.NO_DELETED.getCode()));
        request.setAttribute("tags", backstageTagService.getAllTags());
        return "backstage/blog_handle";
    }

    @ApiOperation(value = "跳转<发布博客>页面编辑", notes = "发布博客", httpMethod = "GET", tags = "博客管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogId", value = "博客ID", dataType = "long", paramType = "path", required = true, defaultValue = "1")
    })
    @GetMapping("/{blogId:\\d+}")
    public String editPage(HttpServletRequest request, @NotNull(message = "请输入博客ID") @PathVariable("blogId") Long blogId) {
        LOGGER.info("进入blogId={}编辑博客页面", blogId);
        request.setAttribute("path", AllConstants.Path.EDIT);
        BlogsBO blog = backstageBlogService.getBlogById(blogId, DeletedEnum.NO_DELETED.getCode());
        if (Objects.isNull(blog)) {
            LOGGER.error("当前博客不存在,blogId=", blogId);
            return "error/error_400";
        }
        request.setAttribute("blog", blog);
        request.setAttribute("categories", backstageCategoryService.getAllCategories(DeletedEnum.NO_DELETED.getCode()));
        request.setAttribute("tags", backstageTagService.findIsSelected(blog));
        return "backstage/blog_handle";
    }

    @ApiOperation(value = "跳转<博客管理>页面", notes = "博客管理", httpMethod = "GET", tags = "博客管理API")
    @GetMapping("/manage")
    public String managePage(HttpServletRequest request) {
        LOGGER.info("进入<博客管理>页面");
        request.setAttribute("path", AllConstants.Path.BLOGS);
        return "backstage/blog_list";
    }

    @ApiOperation(value = "新增博客信息到数据库", notes = "发布博客", httpMethod = "POST", tags = "博客管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/")
    @ResponseBody
    public ResultVO saveBlog(@ApiParam(name = "博客新增对象", value = "传入json格式", required = true)
                             @Validated @RequestBody BlogsVO blogsVO) {
        LOGGER.info("新增博客:{}", JSON.toJSONString(blogsVO));
        ResultVO resultVO = new ResultVO();
        BlogsBO blogsBO = new BlogsBO();
        try {
            BeanUtils.copyProperties(blogsVO, blogsBO);
            backsatgeBiz.saveBlog(blogsBO);
            resultVO.returnSuccess();
        } catch (SiteException bize) {
            LOGGER.warn("博客新增失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("博客新增出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "分页查询博客信息", notes = "博客管理", httpMethod = "GET", tags = "博客管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "long", paramType = "query", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "keyword", value = "标题/分类", dataType = "string", paramType = "query", defaultValue = "java")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'List<BlogsBO>'}")
    })
    @GetMapping("/")
    @ResponseBody
    public ResultVO<BlogsVO> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        LOGGER.info("博客分页查询:{}", JSON.toJSONString(params));
        ResultVO<BlogsVO> resultVO = new ResultVO<>();
        BlogsVO blogsVO = new BlogsVO();
        BlogsBO blogsBO = new BlogsBO();
        try {
            if (StringUtils.isEmpty(params.get("page")) ||
                    StringUtils.isEmpty(params.get("limit"))) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            blogsBO.setPage(Integer.parseInt(String.valueOf(params.get("page"))) - 1);
            blogsBO.setLimit(Integer.parseInt(String.valueOf(params.get("limit"))));
            blogsBO.setFormatDate(AllConstants.Common.DATE_FORMAT_YYYYMMDD_HH_MM_SS);
            backstageBlogService.queryBlogByPageBackstage(Objects.isNull(params.get("keyword"))
                    ? null : String.valueOf(params.get("keyword")), blogsBO);
            BeanUtils.copyProperties(blogsBO, blogsVO);
            resultVO.returnSuccessWithData(blogsVO);
        } catch (SiteException bize) {
            LOGGER.warn("博客分页查询失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("博客分页查询出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }


    @ApiOperation(value = "更新博客信息到数据库", notes = "发布博客", httpMethod = "POST", tags = "博客管理API")
    @ApiImplicitParams({@ApiImplicitParam(name = "blogId", value = "博客ID", dataType = "long", paramType = "path", required = true, defaultValue = "1")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/{blogId:\\d+}")
    @ResponseBody
    public ResultVO update(@ApiParam(name = "博客修改对象", value = "传入json格式", required = true)
                           @Validated @RequestBody BlogsVO blogsVO,
                           @NotNull(message = "请输入博客ID") @PathVariable("blogId") Long blogId) {
        LOGGER.info("更新博客id={}信息为:{}", blogId, JSON.toJSONString(blogsVO));
        ResultVO resultVO = new ResultVO();
        BlogsBO blogsBO = new BlogsBO();
        try {
            BeanUtils.copyProperties(blogsVO, blogsBO);
            blogsBO.setId(blogId);
            backsatgeBiz.updateBlog(blogsBO);
            resultVO.returnSuccess();
        } catch (SiteException bize) {
            LOGGER.warn("博客更新失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("博客更新出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "删除博客信息", notes = "博客管理", httpMethod = "DELETE", tags = "博客管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @DeleteMapping("/")
    @ResponseBody
    public ResultVO delete(@ApiParam(name = "博客ID", value = "请求url匹配", required = true, type = "array")
                           @NotNull(message = "请选择需要删除的博客id") @RequestBody Integer[] ids) {
        LOGGER.info("删除博客，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backsatgeBiz.deleteBlog(ids);
            resultVO.returnSuccess();
        } catch (SiteException bize) {
            LOGGER.warn("博客删除失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("博客删除出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }


}
