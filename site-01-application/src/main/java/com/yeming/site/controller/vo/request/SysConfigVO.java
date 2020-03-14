package com.yeming.site.controller.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yeming.gao
 * @Description: 系统配置请求对象
 * @date 2020/2/28 11:22
 */
@ApiModel(value = "sysConfigVO对象", description = "系统配置操作对象")
@Setter
@Getter
public class SysConfigVO extends CommonVO{

    /**
     * 网站相关配置
     */
    @ApiModelProperty(value = "站点名称", name = "websiteName", example = "https://www.baidu.com/")
    @Length(max = 200, message = "站点名称过长")
    private String websiteName;

    @ApiModelProperty(value = "站点描述", name = "websiteDescription", example = "百度")
    @Length(max = 200, message = "站点描述过长")
    private String websiteDescription;

    @ApiModelProperty(value = "站点Logo", name = "websiteLogo", example = "/admin/dist/img/logo2.png")
    @Length(max = 200, message = "站点Logo过长")
    private String websiteLogo;

    @ApiModelProperty(value = "站点favicon.ico", name = "websiteIcon", example = "/admin/dist/img/favicon.png")
    @Length(max = 200, message = "站点favicon.ico过长")
    private String websiteIcon;


    /**
     * 个人信息相关配置
     */
    @ApiModelProperty(value = "个人头像", name = "yourAvatar", example = "/admin/dist/img/personal.jpg")
    @Length(max = 200, message = "个人头像过长")
    private String yourAvatar;

    @ApiModelProperty(value = "个人名称", name = "yourName", example = "我爱2B哥")
    @Length(max = 200, message = "个人名称过长")
    private String yourName;

    @ApiModelProperty(value = "个人邮箱", name = "yourEmail", example = "yeming.gao@aliyun.com")
    @Length(max = 200, message = "个人邮箱过长")
    private String yourEmail;

    /**
     * 底部信息相关配置
     */
    @ApiModelProperty(value = "底部About", name = "footerAbout", example = "your personal blog. have fun.")
    @Length(max = 200, message = "底部About过长")
    private String footerAbout;

    @ApiModelProperty(value = "底部备案号", name = "footerICP", example = "浙ICP备 xxxxxxxx号-3")
    @Length(max = 200, message = "底部备案号过长")
    private String footerICP;

    @ApiModelProperty(value = "底部Copy Right", name = "footerCopyRight", example = "2020 @ 我爱2B哥")
    @Length(max = 200, message = "底部Copy Right过长")
    private String footerCopyRight;

    @ApiModelProperty(value = "底部Powered By", name = "footerPoweredBy", example = "https://github.com/gaoyeming/my_site")
    @Length(max = 200, message = "底部Powered By过长")
    private String footerPoweredBy;

    @ApiModelProperty(value = "底部Powered By URL", name = "footerPoweredByURL", example = "https://github.com/gaoyeming/my_site")
    @Length(max = 200, message = "底部Powered By URL过长")
    private String footerPoweredByURL;

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("websiteName", getWebsiteName())
                .add("websiteDescription", getWebsiteDescription())
                .add("websiteLogo", getWebsiteLogo())
                .add("websiteIcon", getWebsiteIcon())
                .add("yourAvatar", getYourAvatar())
                .add("yourName", getYourName())
                .add("yourEmail", getYourEmail())
                .add("footerAbout", getFooterAbout())
                .add("footerICP", getFooterICP())
                .add("footerCopyRight", getFooterCopyRight())
                .add("footerPoweredBy", getFooterPoweredBy())
                .add("footerPoweredByURL", getFooterPoweredByURL()).toString();
    }
}
