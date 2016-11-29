
package com.kaffatech.latte.mainframe.dto;

import com.kaffatech.latte.commons.bean.model.BaseBean;

/**
 * @author zhen.ling
 */
public class Request extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String channel;
    private String osVer;
    private String manufacturer;
    private String model;

    private String device;

    private String protocolVer;

    private String appVer;

    private String token;

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the osVer
     */
    public String getOsVer() {
        return osVer;
    }

    /**
     * @param osVer the osVer to set
     */
    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the protocolVer
     */
    public String getProtocolVer() {
        return protocolVer;
    }

    /**
     * @param protocolVer the protocolVer to set
     */
    public void setProtocolVer(String protocolVer) {
        this.protocolVer = protocolVer;
    }

    /**
     * @return the appVer
     */
    public String getAppVer() {
        return appVer;
    }

    /**
     * @param appVer the appVer to set
     */
    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

}
