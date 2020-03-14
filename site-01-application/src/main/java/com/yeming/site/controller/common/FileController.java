package com.yeming.site.controller.common;

import com.yeming.site.config.PropertiesConfig;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.util.DateUtils;
import com.yeming.site.util.constant.AllConstants;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;

/**
 * @author yeming.gao
 * @Description: 博客相关接口
 * @date 2020/2/24 16:00
 */
@Api(value = "FileController", description = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {

    private static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Resource
    private PropertiesConfig propertiesConfig;


    @ApiOperation(value = "普通文件上传", notes = "上传文件到服务器指定的目录",
            httpMethod = "POST", tags = "文件上传API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'000','message':'交易成功'}")
    })
    @PostMapping("/upload")
    public ResultVO uploadFile(@ApiParam(value = "上传的文件", required = true)
                               @RequestParam(name = "image-file") MultipartFile file) {
        ResultVO<String> resultVO = new ResultVO<>();
        OutputStream outputStream = null;
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf(AllConstants.Common.SPLIT_POINT));
            //生成文件名称通用方法
            Random r = new Random();
            String newFileName = AllConstants.Common.PREFIX_FM +
                    DateUtils.getCurrentDateToStr(AllConstants.Common.DATE_FORMAT_YYYYMMDD_HHMMSS) +
                    r.nextInt(1000) + suffixName;
            //创建文件
            File destFile = new File(propertiesConfig.getFileUploadDic() + newFileName);
            File fileDirectory = new File(propertiesConfig.getFileUploadDic());
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    LOGGER.warn("{}目录创建失败", fileDirectory.getPath());
                }
            }
            outputStream = new FileOutputStream(destFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            outputStream.flush();
            //上传成功设置可以查看的url
//            String fileUrl = CommonUtils.getHost(new URI(AllConstants.Web.STATIC_HTTP_REQUEST_URL))+"" + newFileName;
            String fileUrl = propertiesConfig.getHttpStaticUrl() + AllConstants.Common.SPLIT_BIAS + newFileName;
            resultVO.returnSuccessWithData(fileUrl);
        } catch (IOException e) {
            LOGGER.error("文件上传出现异常:", e);
            resultVO.genFailResult("文件上传失败");
        } finally {
            if (Objects.nonNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("outputStream close fail...");
                }
            }
        }
        return resultVO;
    }

    @ApiOperation(value = "MARKDOWN文件上传", notes = "上传文件到服务器指定的目录",
            httpMethod = "POST", tags = "文件上传API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{success : 1, message : '上传成功！',url: fileUrl}")
    })
    @PostMapping("/markdown/upload")
    public String markdownUploadImage(@ApiParam(value = "上传的文件", required = true)
                                      @RequestParam(name = "editormd-image-file") MultipartFile file) {
        String returnStr;
        OutputStream outputStream = null;
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf(AllConstants.Common.SPLIT_POINT));
            //生成文件名称通用方法
            Random r = new Random();
            String newFileName = AllConstants.Common.PREFIX_MD +
                    DateUtils.getCurrentDateToStr(AllConstants.Common.DATE_FORMAT_YYYYMMDD_HHMMSS) +
                    r.nextInt(1000) + suffixName;
            //创建文件
            File destFile = new File(propertiesConfig.getFileUploadDic() + newFileName);
            File fileDirectory = new File(propertiesConfig.getFileUploadDic());
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    LOGGER.warn("{}目录创建失败", fileDirectory.getPath());
                }
            }
            outputStream = new FileOutputStream(destFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            outputStream.flush();
            //上传成功设置可以查看的url
//            String fileUrl = CommonUtils.getHost(new URI(AllConstants.Web.STATIC_HTTP_REQUEST_URL))+"" + newFileName;
            String fileUrl = propertiesConfig.getHttpStaticUrl() + AllConstants.Common.SPLIT_BIAS + newFileName;
            returnStr = "{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}";
        } catch (IOException e) {
            LOGGER.error("文件上传出现异常:", e);
            returnStr = "{\"success\":0}";
        } finally {
            if (Objects.nonNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("outputStream close fail...");
                }
            }
        }
        return returnStr;
    }
}
