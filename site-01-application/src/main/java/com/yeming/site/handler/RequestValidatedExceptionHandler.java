package com.yeming.site.handler;

import com.yeming.site.controller.vo.response.ResultVO;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yeming.gao
 * @Description: 请求参数校验异常处理
 * @date 2020/2/29 20:32
 * <p>
 * 异常类型	描述
 * ConstraintViolationException	违反约束，javax扩展定义
 * BindException	绑定失败，如表单对象参数违反约束
 * MethodArgumentNotValidException	参数无效，如JSON请求参数违反约束
 * MissingServletRequestParameterException	参数缺失
 * TypeMismatchException	参数类型不匹配
 */
@RestControllerAdvice
public class RequestValidatedExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultVO<String> handler(MethodArgumentNotValidException e) {
        //按需重新封装需要返回的错误信息
        ResultVO<String> resultVO = new ResultVO<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        StringBuilder strBuilder = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            strBuilder.append(error.getField())
                    .append(":")
                    .append(error.getDefaultMessage());
        }
        String result = strBuilder.toString();
        resultVO.returnRequestFail(result);
        return resultVO;

    }
}
