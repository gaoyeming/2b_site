package com.yeming.site.aop.annotation;

import java.lang.annotation.*;

/**
 * @author yeming.gao
 * @Description: 系统操作日志注解
 * @date 2019/11/26 9:41
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysOperationLog {

    String value() default "";

}
