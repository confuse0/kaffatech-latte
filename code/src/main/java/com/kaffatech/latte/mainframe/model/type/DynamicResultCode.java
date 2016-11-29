package com.kaffatech.latte.mainframe.model.type;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;

/**
 * @author ZM.WANG Created by on 16/10/12.
 */
public class DynamicResultCode implements TypeBean, ResultCode {

    private String code;

    private String name;

    public DynamicResultCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * @return the code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.uuban.toffee.dmo.ResultCode#getMessage()
     */
    @Override
    public String getMessage() {
        return getName();
    }
}
