package com.yeming.site.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author yeming.gao
 * @Description: 参数校验注解配置
 * 普通模式（默认是这个模式）: 会校验完所有的属性，然后返回所有的验证失败信息
 * 快速失败模式: 只要有一个验证失败，则返回
 * @date 2020/2/29 20:24
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast( true )
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}
