package com.kaffatech.latte.coder.db.mybatis;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author Created by Alan on 2017/5/10.
 */
public class SimpleCommentGenerator extends DefaultCommentGenerator {

    /**
     * 生成模型注释
     *
     * @param topLevelClass     类
     * @param introspectedTable 表
     */
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/*");
        topLevelClass.addJavaDocLine(" * 表：" + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 生成字段注释
     *
     * @param field              字段
     * @param introspectedTable  表
     * @param introspectedColumn 表列
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**");
        if (!StringUtils.isEmpty(introspectedColumn.getRemarks())) {
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        }
        field.addJavaDocLine(" * 表字段：" + introspectedColumn.getActualColumnName());
        field.addJavaDocLine(" */");
    }

}
