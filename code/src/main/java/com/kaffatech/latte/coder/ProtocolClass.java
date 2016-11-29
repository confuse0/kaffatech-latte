package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by lingzhen on 15/12/25.
 */
public class ProtocolClass extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 类名
     */
    private String name;

    /**
     * 包名
     */
    private String pkg;

    /**
     * 引入的类列表
     */
    private Set<String> importList;

    /**
     * 属性列表
     */
    private List<ProtocolField> fieldList;

    /**
     * 子类型列表
     */
    private List<ProtocolClass> subClassList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void addImport(String importStr) {
        if (CollectionUtils.isEmpty(importList)) {
            importList = new TreeSet<String>();
        }
        importList.add(importStr);
    }

    public List<ProtocolField> getFieldList() {
        return fieldList;
    }

    public void addField(ProtocolField field) {
        if (CollectionUtils.isEmpty(fieldList)) {
            fieldList = new ArrayList<ProtocolField>();
        }
        fieldList.add(field);
    }

    public List<ProtocolClass> getSubClassList() {
        return subClassList;
    }

    public void setSubClassList(List<ProtocolClass> subClassList) {
        this.subClassList = subClassList;
    }
}
