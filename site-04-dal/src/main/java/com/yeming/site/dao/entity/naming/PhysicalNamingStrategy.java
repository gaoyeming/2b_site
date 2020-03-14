package com.yeming.site.dao.entity.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.util.StringUtils;

/**
 * @author yeming.gao
 * @Description: hibernate.physical-strategy属性，自定义表与对象之间的映射规则
 * @date 2019/11/4 21:44
 */
public class PhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(name.getText(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return convert(name);
    }

    /**
     * 更改Hibernate表名映射规则，保持Entity类中对数据库表命名的不变
     *
     * @param identifier 标识符
     * @return Identifier
     */
    private Identifier convert(Identifier identifier) {
        if (identifier == null || !StringUtils.hasText(identifier.getText())) {
            return identifier;
        }

        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}