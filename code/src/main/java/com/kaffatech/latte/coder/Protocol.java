package com.kaffatech.latte.coder;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * Created by lingzhen on 15/12/24.
 */
public class Protocol extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 接口名
     */
    private String name;

    /**
     * 包
     */
    private String pkg;


    /**
     * 接口描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求名
     */
    private String reqName;

    /**
     * 响应名
     */
    private String resName;

    /**
     * 请求字段
     */
    private ProtocolClass request;

    /**
     * 响应字段
     */
    private ProtocolClass response;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public ProtocolClass getRequest() {
        return request;
    }

    public void setRequest(ProtocolClass request) {
        this.request = request;
    }

    public ProtocolClass getResponse() {
        return response;
    }

    public void setResponse(ProtocolClass response) {
        this.response = response;
    }
}
