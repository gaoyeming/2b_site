package com.yeming.site.controller.blog;

import com.alibaba.fastjson.JSON;
import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.biz.FrontBiz;
import com.yeming.site.biz.BacksatgeBiz;
import com.yeming.site.controller.vo.request.BlogsVO;
import com.yeming.site.controller.vo.request.CommentsVO;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.dao.entity.BackstageLinkDO;
import com.yeming.site.service.common.BackstageBlogCommentService;
import com.yeming.site.service.common.BackstageBlogService;
import com.yeming.site.service.common.BackstageLinkService;
import com.yeming.site.service.dto.BlogsBO;
import com.yeming.site.service.dto.CommentsBO;
import com.yeming.site.util.IPUtils;
import com.yeming.site.util.constant.AllConstants;
import com.yeming.site.util.enums.SelectTypeEnum;
import com.yeming.site.util.exception.SiteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: 我的博客首页
 * @date 2020/3/8 17:15
 */
@Controller
public class FrontBlogController {

    private static Logger LOGGER = LoggerFactory.getLogger(FrontBlogController.class);

    private static final String THEME = "amaze";

    @Resource
    private FrontBiz frontBiz;

    @Resource
    private BacksatgeBiz backsatgeBiz;

    @Resource
    private BackstageBlogService backstageBlogService;

    @Resource
    private BackstageBlogCommentService backstageBlogCommentService;

    @Resource
    private BackstageLinkService backstageLinkService;

    /**
     * 首页
     *
     * @return String 首页
     */
    @GetMapping({"/", "/index", "index.html"})
    @SysOperationLog("访问我的博客")
    public String index(HttpServletRequest request) {
        LOGGER.info("进入博客首页");
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     *
     * @return String
     */
    @GetMapping({"/page/{pageNum}"})
    @SysOperationLog("分页查询我的博客")
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        LOGGER.info("查询第{}页博客信息",pageNum);
        BlogsVO blogsVO = new BlogsVO();
        BlogsBO blogsBO = new BlogsBO();
        blogsBO.setPage(pageNum - 1);
        blogsBO.setLimit(8);
        blogsBO.setFormatDate(AllConstants.Common.DATE_FORMAT_YYYY_MM_DD);
        blogsBO.setSelectTpye(SelectTypeEnum.KEY_WORD.getCode());
        backstageBlogService.queryBlogByPageFront(null, blogsBO);
        if (Objects.isNull(blogsBO.getResultList())) {
            return "error/error_404";
        }
        BeanUtils.copyProperties(blogsBO, blogsVO);
        blogsVO.setCurrPage(pageNum);
        request.setAttribute("blogPageResult", blogsVO);
        request.setAttribute("newBlogs", frontBiz.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", frontBiz.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", frontBiz.getTagUseCount());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "blog/" + THEME + "/index";
    }

    /**
     * 搜索列表页
     */
    @GetMapping({"/search/{keyword}"})
    @SysOperationLog("关键字搜索我的博客")
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return search(request, keyword, 1);
    }

    /**
     * 搜索列表页
     */
    @GetMapping({"/search/{keyword}/{page}"})
    @SysOperationLog("关键字搜索后分页查询我的博客")
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        LOGGER.info("第{}页关键字搜索博客信息:{}", page, keyword);
        BlogsVO blogsVO = new BlogsVO();
        BlogsBO blogsBO = new BlogsBO();
        blogsBO.setPage(page - 1);
        blogsBO.setLimit(8);
        blogsBO.setFormatDate(AllConstants.Common.DATE_FORMAT_YYYY_MM_DD);
        blogsBO.setSelectTpye(SelectTypeEnum.KEY_WORD.getCode());
        backstageBlogService.queryBlogByPageFront(keyword, blogsBO);
        if (Objects.isNull(blogsBO.getResultList())) {
            return "error/error_404";
        }
        BeanUtils.copyProperties(blogsBO, blogsVO);
        blogsVO.setCurrPage(page);
        request.setAttribute("blogPageResult", blogsVO);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", frontBiz.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", frontBiz.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", frontBiz.getTagUseCount());
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "blog/" + THEME + "/list";
    }

    /**
     * 分类列表页
     */
    @GetMapping({"/category/{categoryId}"})
    @SysOperationLog("分类搜索我的博客")
    public String category(HttpServletRequest request, @PathVariable("categoryId") Integer categoryId) {
        return category(request, categoryId, 1);
    }

    /**
     * 分类列表页
     */
    @GetMapping({"/category/{categoryId}/{page}"})
    @SysOperationLog("分类搜索后分页查询我的博客")
    public String category(HttpServletRequest request, @PathVariable("categoryId") Integer categoryId, @PathVariable("page") Integer page) {
        LOGGER.info("第{}页分类搜索博客信息:{}", page, categoryId);
        BlogsVO blogsVO = new BlogsVO();
        BlogsBO blogsBO = new BlogsBO();
        blogsBO.setPage(page - 1);
        blogsBO.setLimit(8);
        blogsBO.setFormatDate(AllConstants.Common.DATE_FORMAT_YYYY_MM_DD);
        blogsBO.setSelectTpye(SelectTypeEnum.CATEGORY.getCode());
        blogsBO.setBlogCategoryId(categoryId);
        backstageBlogService.queryBlogByPageFront(null, blogsBO);
        if (Objects.isNull(blogsBO.getResultList())) {
            return "error/error_404";
        }
        BeanUtils.copyProperties(blogsBO, blogsVO);
        blogsVO.setCurrPage(page);
        request.setAttribute("blogPageResult", blogsVO);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryId);
        request.setAttribute("newBlogs", frontBiz.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", frontBiz.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", frontBiz.getTagUseCount());
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "blog/" + THEME + "/list";
    }

    /**
     * 标签列表页
     */
    @GetMapping({"/tag/{tagId}"})
    @SysOperationLog("标签搜索我的博客")
    public String tag(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
        return tag(request, tagId, 1);
    }

    /**
     * 标签列表页
     */
    @GetMapping({"/tag/{tagId}/{page}"})
    @SysOperationLog("标签搜索后分页查询我的博客")
    public String tag(HttpServletRequest request, @PathVariable("tagId") Integer tagId, @PathVariable("page") Integer page) {
        LOGGER.info("第{}页标签搜索博客信息:{}", page, tagId);
        BlogsVO blogsVO = new BlogsVO();
        BlogsBO blogsBO = new BlogsBO();
        blogsBO.setPage(page - 1);
        blogsBO.setLimit(8);
        blogsBO.setFormatDate(AllConstants.Common.DATE_FORMAT_YYYY_MM_DD);
        blogsBO.setSelectTpye(SelectTypeEnum.TAG.getCode());
        blogsBO.setBlogTagId(tagId);
        backstageBlogService.queryBlogByPageFront(null, blogsBO);
        if (Objects.isNull(blogsBO.getResultList())) {
            return "error/error_404";
        }
        BeanUtils.copyProperties(blogsBO, blogsVO);
        blogsVO.setCurrPage(page);
        request.setAttribute("blogPageResult", blogsVO);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagId);
        request.setAttribute("newBlogs", frontBiz.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", frontBiz.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", frontBiz.getTagUseCount());
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());

        return "blog/" + THEME + "/list";
    }

    /**
     * 博客详情页
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    @SysOperationLog("查看我的博客详情")
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId,
                         @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogsBO blogsBO = frontBiz.getBlogDetail(blogId);
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        if (Objects.nonNull(blogsBO)) {
            request.setAttribute("blogDetailVO", blogsBO);
            //评论分页查询
            CommentsBO commentsBO = new CommentsBO();
            commentsBO.setPage(commentPage - 1);
            commentsBO.setLimit(8);
            backstageBlogCommentService.queryBlogByPage(commentsBO);
            CommentsVO commentsVO = new CommentsVO();
            BeanUtils.copyProperties(commentsBO, commentsVO);
            commentsVO.setCurrPage(commentPage);
            request.setAttribute("commentPageResult", commentsVO);
            request.setAttribute("pageName", blogsBO.getBlogSubUrl());
        } else {
            request.setAttribute("pageName", "sorry,当前博客不存在");
            return "error/error_404";
        }

        return "blog/" + THEME + "/detail";
    }

    /**
     * 友情链接页
     */
    @GetMapping({"/link"})
    @SysOperationLog("查看我的友链详情")
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "友情链接");
        Map<Integer, List<BackstageLinkDO>> linkMap = backstageLinkService.getAllLinks();
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey(0)) {
                request.setAttribute("favoriteLinks", linkMap.get(0));
            }
            if (linkMap.containsKey(1)) {
                request.setAttribute("recommendLinks", linkMap.get(1));
            }
            if (linkMap.containsKey(2)) {
                request.setAttribute("personalLinks", linkMap.get(2));
            }
        }
        request.setAttribute("configurations", backsatgeBiz.getAllConfigs());
        return "blog/" + THEME + "/link";
    }

    @PostMapping("/blog/comment")
    @ResponseBody
    @SysOperationLog("评论我的博客")
    public ResultVO saveComment(HttpServletRequest request, HttpSession session,
                                @Validated @RequestBody CommentsVO commentsVO) {
        LOGGER.info("新增评论信息:{}", JSON.toJSONString(commentsVO));
        ResultVO resultVO = new ResultVO();
        CommentsBO commentsBO = new CommentsBO();
        try {
            //首先校验验证码
            String kaptchaCode = session.getAttribute("verifyCode") + "";
            if (!commentsVO.getVerifyCode().equals(kaptchaCode)) {
                throw new SiteException("验证码错误");
            }
            //获取ip
            String ip = IPUtils.getIpAddr(request);
            BeanUtils.copyProperties(commentsVO, commentsBO);
            commentsBO.setCommentatorIp(ip);
            backstageBlogCommentService.saveComments(commentsBO);
            resultVO.returnSuccess();
        } catch (SiteException bize) {
            LOGGER.warn("评论新增失败:{}", bize.getMessage());
            resultVO.returnException(bize);
        } catch (Exception e) {
            LOGGER.info("评论新增出现异常:", e);
            resultVO.returnSystemError();
        }
        return resultVO;
    }

}
