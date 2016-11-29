package com.kaffatech.latte.mainframe.dto;

/**
 * Created by lingzhen on 16/5/17.
 */
public class RedirectReq extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 跳转URL
     */
    private String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
