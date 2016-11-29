
package com.kaffatech.latte.mainframe.dto.file;

import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.mainframe.dto.Response;

/**
 *
 *
 */
public class GetUptokenRes extends Response {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String uptoken;

    /**
     * @return the uptoken
     */
    public String getUptoken() {
        return uptoken;
    }

    /**
     * @param uptoken the uptoken to set
     */
    public void setUptoken(String uptoken) {
        this.uptoken = uptoken;
        setCode(BaseResultCode.SUCCESS.getCode());
        setMessage(BaseResultCode.SUCCESS.getMessage());
    }

}
