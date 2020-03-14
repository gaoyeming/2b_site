package com.yeming.site.aop.annotation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;


/**
 * @author yeming.gao
 * @Description: 校验枚举值有效性
 * @date 2020/2/28 13:34
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.EnumValidatorHandle.class)
public @interface EnumValidator {

    Class<?> enumClass ();

    String message() default "入参值不在正确枚举中";

    String method() default "getCode";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @author tlj
     * @date 2019/7/9
     */
    @Slf4j
    class EnumValidatorHandle implements ConstraintValidator<EnumValidator, Object>, Annotation {
        private List<Object> values = new ArrayList<>();

        @Override
        public void initialize(EnumValidator enumValidator) {
            Class<?> clz = enumValidator.enumClass();
            Object[] objects = clz.getEnumConstants();
            try {
                Method method = clz.getMethod(enumValidator.method());
                if (Objects.isNull(method)) {
                    log.error("枚举参数校验注解{}方法不存在", enumValidator.method());
                    throw new RuntimeException(String.format("枚举对象%s缺少名为%s的方法", clz.getName(), enumValidator.method()));
                }
                Object value;
                for (Object obj : objects) {
                    value = method.invoke(obj);
                    values.add(value);
                }
            } catch (Exception e) {
                log.error("枚举参数校验注解处理异常:{}", e);
            }
        }


        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value instanceof String) {
                String valueStr = (String) value;
                return StringUtils.isEmpty(valueStr) || values.contains(value);
            }
            return Objects.isNull(value) || values.contains(value);
        }
    }
}
