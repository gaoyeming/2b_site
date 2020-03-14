package com.yeming.site.controller.barrage;

import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.biz.BarrageBiz;
import com.yeming.site.controller.vo.response.ResultVO;
import com.yeming.site.util.exception.SiteException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yeming.gao
 * @Description: 弹幕系统控制层
 * @date 2019/11/25 16:13
 */
@Api(value = "BarrageController", description = "弹幕相关API")
@RestController
@RequestMapping("/barrage")
public class BarrageController {

    @Resource
    private BarrageBiz barrageBiz;

    @PostMapping("/send")
    @ApiOperation(value = "发送弹幕消息", notes = "将消息发送到弹幕窗口", httpMethod = "POST", tags = "弹幕管理相关API")
    @ApiImplicitParams({@ApiImplicitParam(name = "content", value = "消息内容", dataType = "String", paramType = "query", required = true)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{'code':'0000','message':'交易成功'}")
    })
    @SysOperationLog("发送弹幕消息")
    public ResultVO send(@RequestParam("content") String content) {
        ResultVO resultVO = new ResultVO();
        try {
            barrageBiz.sendInfo(content);
            resultVO.returnSuccess();
        } catch (SiteException re) {
            resultVO.returnException(re);
        } catch (Exception e) {
            resultVO.returnSystemError();
        }
        return resultVO;
    }
}
