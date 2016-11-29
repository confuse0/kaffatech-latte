package com.kaffatech.latte.security.model.type;

/**
 * @author lingzhen on 16/9/18.
 */
import com.kaffatech.latte.commons.bean.model.type.TypeBean;

public enum SignType implements TypeBean {

    RSA("RSA", "SHA1WithRSA");

    private String code;

    private String name;

    SignType(String code, String name) {
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
     * @param code
     *            the code to set
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
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
