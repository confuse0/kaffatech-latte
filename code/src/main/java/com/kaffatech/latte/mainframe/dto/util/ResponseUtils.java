
package com.kaffatech.latte.mainframe.dto.util;

import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.mainframe.model.type.ResultCode;
import com.kaffatech.latte.mainframe.model.exception.BizException;
import com.kaffatech.latte.mainframe.dto.LoginErrorRes;
import com.kaffatech.latte.mainframe.dto.Response;

/**
 * @author zhen.ling
 */
public class ResponseUtils {

    /**
     * 创建成功ResponseDto
     *
     * @return
     */
    public static Response createSuccess() {
        return create(BaseResultCode.SUCCESS);
    }

    /**
     * 创建成功ResponseDto
     *
     * @param res
     * @return
     */
    public static <T> T createSuccess(T res) {
        ((Response)res).setCode(BaseResultCode.SUCCESS.getCode());
        ((Response)res).setMessage(BaseResultCode.SUCCESS.getMessage());
        return res;
    }

    /**
     * 创建系统错误ResponseDto
     *
     * @return
     */
    public static Response createSysError() {
        return create(BaseResultCode.SYS_ERROR);
    }


    /**
     * 创建登陆错误Dto
     *
     * @param url
     * @return
     */
    public static LoginErrorRes createLoginErrorRes(String url) {
        LoginErrorRes res = new LoginErrorRes();
        res.setLoginUrl(url);
        res.setCode(BaseResultCode.NEED_LOGIN.getCode());
        res.setMessage(BaseResultCode.NEED_LOGIN.getMessage());

        return res;
    }

    /**
     * 根据结果码创建ResponseDto
     *
     * @param code
     * @return
     */
    public static Response create(ResultCode code) {
        return create(code.getCode(), code.getMessage());
    }

    /**
     * 根据异常创建ResponseDto
     *
     * @param
     * @return
     */
    public static Response create(BizException e) {
        return create(e.getCode(), e.getMessage());
    }

    /**
     * 创建ResponseDto
     *
     * @param code
     * @param message
     * @return
     */
    public static Response create(String code, String message) {
        Response result = new Response();
        result.setCode(code);
        result.setMessage(message);

        return result;
    }
}
