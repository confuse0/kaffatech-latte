package com.kaffatech.latte.coder.db;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.List;

/**
 * @author Created by Alan on 2017/5/10.
 */
public class SimpleCommentGenerator extends DefaultCommentGenerator {

    /**
     * 生成方法注释
     *
     * @param method            方法
     * @param introspectedTable 表
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");
        String method_name = method.getName();

        if ("deleteByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            method.addJavaDocLine(" * 插入数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键获取一条数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键来更新数据库记录");
        } else if ("selectAll".equals(method_name)) {
            method.addJavaDocLine(" * 获取全部数据库记录");
        }
        method.addJavaDocLine(" *");
        List<Parameter> parameterList = method.getParameters();
        String parameterName;
        for (Parameter parameter : parameterList) {
            parameterName = parameter.getName();
            method.addJavaDocLine(" * @param " + parameterName);
        }
        method.addJavaDocLine(" */");
    }

    /**
     * 生成类注释
     *
     * @param innerClass        类
     * @param introspectedTable 表
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        String shortName = innerClass.getType().getShortName();
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * 类注释");
        innerClass.addJavaDocLine(" * " + shortName);
        innerClass.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 生成类注释
     *
     * @param innerClass        类
     * @param introspectedTable 表
     * @param markAsDoNotDelete 标记为不删除
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        String shortName = innerClass.getType().getShortName();
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * 类注释(DoNotDelete)");
        innerClass.addJavaDocLine(" * " + shortName);
        innerClass.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" */");
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
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        if (introspectedColumn.getRemarks() != null)
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        sb.append(" * 表字段 : ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

}
