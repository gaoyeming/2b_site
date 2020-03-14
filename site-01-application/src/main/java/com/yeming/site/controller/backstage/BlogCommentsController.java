package com.yeming.site.controller.backstage;

import com.alibaba.fastjson.JSON;
import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.controller.vo.request.CommentsVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.service.common.BackstageBlogCommentService;
import com.yeming.site.service.dto.CommentsBO;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.RespCodeEnum;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 博客评论管理
 * @date 2020/3/4 16:59
 */
@Api(value = "BlogCommentsController", description = "评论管理接口")
@Controller
@RequestMapping("/backstage/comments")
public class BlogCommentsController {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogCommentsController.class);

    @Resource
    private BackstageBlogCommentService backstageBlogCommentService;

    @ApiOperation(value = "跳转<评论管理>页面", notes = "评论管理", httpMethod = "GET", tags = "评论管理API")
    @GetMapping("/manage")
    public String managePage(HttpServletRequest request) {
        LOGGER.info("进入<评论管理>页面");
        request.setAttribute("path", AllConstants.Path.COMMENTS);
        return "backstage/comment_handle";
    }

    @ApiOperation(value = "分页查询评论信息", notes = "评论管理", httpMethod = "GET", tags = "评论管理API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "long", paramType = "query", required = true, defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功','data':'List<CommentsBO>'}")
    })
    @GetMapping("/")
    @ResponseBody
    public ResultVO<CommentsVO> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        LOGGER.info("评论分页查询:{}", JSON.toJSONString(params));
        ResultVO<CommentsVO> resultVO = new ResultVO<>();
        CommentsVO commentsVO = new CommentsVO();
        CommentsBO commentsBO = new CommentsBO();
        try {
            if (StringUtils.isEmpty(params.get("page")) ||
                    StringUtils.isEmpty(params.get("limit"))) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            commentsBO.setPage(Integer.parseInt(String.valueOf(params.get("page"))) - 1);
            commentsBO.setLimit(Integer.parseInt(String.valueOf(params.get("limit"))));
            backstageBlogCommentService.queryBlogByPage(commentsBO);
            BeanUtils.copyProperties(commentsBO, commentsVO);
            resultVO.returnSuccessWithData(commentsVO);
            LOGGER.info("评论分页查询结果:{}", JSON.toJSONString(commentsVO.getResultList()));
        } catch (SiteException bize) {
            LOGGER.warn("评论分页查询失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("评论分页查询出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "审核评论信息", notes = "评论管理", httpMethod = "POST", tags = "评论管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/checkDone")
    @ResponseBody
    public ResultVO checkDone(@ApiParam(name = "评论ID", value = "请求url匹配", required = true, type = "array")
            @NotNull(message = "请选择需要审核评论id") @RequestBody Integer[] ids) {
        LOGGER.info("审核评论，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backstageBlogCommentService.checkDone(ids);
            resultVO.returnSuccess();
            LOGGER.info("ids={}所有评论全部审核通过", JSON.toJSONString(ids));
        } catch (SiteException bize) {
            LOGGER.warn("审核评论失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("审核评论出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "删除评论信息", notes = "评论管理", httpMethod = "DELETE", tags = "评论管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @DeleteMapping("/")
    @ResponseBody
    public ResultVO delete(@ApiParam(name = "评论ID", value = "请求url匹配", required = true, type = "array")
            @NotNull(message = "请选择需要删除评论id") @RequestBody Integer[] ids) {
        LOGGER.info("删除评论，ids={}", JSON.toJSONString(ids));
        ResultVO resultVO = new ResultVO();
        try {
            if (ids.length < 1) {
                throw new SiteException(RespCodeEnum.REQUEST_FAIL);
            }
            backstageBlogCommentService.deleteComments(ids);
            resultVO.returnSuccess();
            LOGGER.info("ids={}所有评论全部删除成功", JSON.toJSONString(ids));
        } catch (SiteException bize) {
            LOGGER.warn("评论删除失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("评论删除出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

    @ApiOperation(value = "回复评论信息", notes = "评论管理", httpMethod = "POST", tags = "评论管理API")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")})
    @PostMapping("/reply")
    @ResponseBody
    public ResultVO reply(@ApiParam(name = "评论ID", value = "需要回复的评论ID", required = true, type = "long")
            @NotNull(message = "请选择需要回复评论id") @RequestParam(name = "commentId") Long commentId,
                          @ApiParam(name = "回复内容", value = "需要回复的评论内容", required = true, type = "string")
                          @NotEmpty(message = "请输入回复内容") @RequestParam(name = "replyBody") String replyBody) {
        LOGGER.info("评论id={}回复得内容为:{}", commentId,replyBody);
        ResultVO resultVO = new ResultVO();
        try {
            backstageBlogCommentService.reply(commentId,replyBody);
            resultVO.returnSuccess();
            LOGGER.info("评论id={}回复成功", commentId);
        } catch (SiteException bize) {
            LOGGER.warn("评论回复失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("评论回复出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
