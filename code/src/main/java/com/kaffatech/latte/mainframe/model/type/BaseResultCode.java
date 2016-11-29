package com.kaffatech.latte.mainframe.model.type;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;

/**
 * @author zhen.ling
 */
public enum BaseResultCode implements TypeBean, ResultCode {
    // 错误码格式:1位错误类型+2位具体类型标识+2位具体错误码;
    // 错误类型:
    // 0-提交成功;
    // 1-保留;
    // ......(业务可以用的有2,3,4,5,6,7)
    // 8-通用业务错误;
    // 9-通用技术错误;

    // --------------- 提交成功,与业务处理是否成功无关 ---------------
    SUCCESS("00000", "提交成功"),

    // --------------- 通用业务错误 ---------------

    // 通用业务-参数810
    INVALID_TICKET("81000", "无效的ticket"),

    TICKET_EXPIRE("81001", "ticket已过期"),

    TICKET_ERROR_TIMES_OUT_OF_LIMIT("81002", "ticket使用次数超限"),

    TICKET_USED("81003", "ticket已经推进成功"),

    DUPLICATE_OPERATION("81030", "禁止提交重复的操作"),

    DUPLICATE_BIND("81035", "禁止重复绑定操作"),

    // 通用业务-短信830
    SEND_CHECK_CODE_SHORT("83000", "发送校验码间隔太短(1分钟内最多发送1次,半小时内最多发送3次,1天最多发送10次)"),

    // 通用业务-统一登陆850
    NEED_LOGIN("85000", "用户权限不足,请登录"),

    // 通用业务-安全870
    IP_DIFFER_ERROR("87000", "操作来源IP不一致"),


    // 通用业务-网关未知异常890
    GW_SYS_ERROR("89000", "网关系统错误"),

    // --------------- 通用技术错误 ---------------

    // 通用错误900
    SOMETHING_ERROR("90000", "系统发生通用错误"),
    SO_BUSY("90099", "系统繁忙,请稍后再试"),

    // 请求参数910
    ILLEGAL_REQ_PARAMETER("91000", "非法的请求参数"),

    ILLEGAL_SIGN("91010", "非法的签名数据"),

    // 文件问题930
    NO_FILE("93000", "文件不存在"),

    CREATE_FILE_FAILURE("93001", "文件生成失败"),

    // 技术权限970
    INVALID_REQ("97000", "请求权限不足"),

    // 终极错误,系统未知异常,业务处理未决
    SYS_ERROR("99999", "系统异常");

    private String code;

    private String name;

    BaseResultCode(String code, String name) {
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
