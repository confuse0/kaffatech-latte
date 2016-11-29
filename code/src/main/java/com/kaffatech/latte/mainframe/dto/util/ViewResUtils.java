
package com.kaffatech.latte.mainframe.dto.util;

import java.io.StringWriter;

import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.ctx.base.ApplicationContextHolder;
import com.kaffatech.latte.mainframe.dto.ViewRes;
import com.kaffatech.latte.mainframe.model.exception.BizException;
import com.kaffatech.latte.mainframe.model.type.ResultCode;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * @author zhen.ling
 */
public class ViewResUtils {

    /**
     * 创建成功ViewRes
     *
     * @param viewName
     * @return
     */
    public static ViewRes createSuccess(String viewName) {
        return createSuccess(viewName, null);
    }

    /**
     * 创建成功ViewRes
     *
     * @param viewName
     * @param res
     * @return
     */
    public static ViewRes createSuccess(String viewName, Object res) {
        return createSuccess(viewName, res, null);
    }

    public static ViewRes createSuccess(String viewName, Object res, Object req) {
        return createByView(BaseResultCode.SUCCESS, merge(viewName, res, req));
    }

    /**
     * 创建系统错误ViewRes
     *
     * @return
     */
    public static ViewRes createSysError() {
        return createByView(BaseResultCode.SYS_ERROR, null);
    }

    /**
     * 根据结果码创建ViewRes
     *
     * @param code
     * @param viewName
     * @return
     */
    public static ViewRes create(ResultCode code, String viewName) {
        return create(code, viewName, null);
    }

    /**
     * 根据结果码创建ViewRes
     *
     * @param code
     * @param viewName
     * @param res
     * @return
     */
    public static ViewRes create(ResultCode code, String viewName, Object res) {
        return createByView(code.getCode(), code.getMessage(), merge(viewName, res));
    }

    /**
     * 根据异常创建ViewRes
     *
     * @param
     * @return
     */
    public static ViewRes create(BizException e) {
        return createByView(e.getCode(), e.getMessage(), null);
    }

    /**
     * 创建ViewRes
     *
     * @param code
     * @param message
     * @param viewName
     * @return
     */
    public static ViewRes create(String code, String message, String viewName) {
        return create(code, message, viewName, null);
    }

    /**
     * 创建ViewRes
     *
     * @param code
     * @param message
     * @param viewName
     * @param res
     * @return
     */
    public static ViewRes create(String code, String message, String viewName, Object res) {
        return createByView(code, message, merge(viewName, res));
    }

    private static ViewRes createByView(ResultCode resultCode, String view) {
        ViewRes result = new ViewRes();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setView(view);

        return result;
    }

    /**
     * 创建ViewRes
     *
     * @param code
     * @param message
     * @param view
     * @return
     */
    private static ViewRes createByView(String code, String message, String view) {
        ViewRes result = new ViewRes();
        result.setCode(code);
        result.setMessage(message);
        result.setView(view);

        return result;
    }

    private static String merge(String viewName, Object res) {
        // 获取模版
        return merge(viewName, res, null);
    }

    /**
     * 合并
     *
     * @param viewName
     * @param res
     * @param req
     * @return
     */
    private static String merge(String viewName, Object res, Object req) {
        // 获取模版
        VelocityConfigurer velocityConfigurer = (VelocityConfigurer) ApplicationContextHolder
                .getBean("velocityConfigurer");
        Template template = (Template) velocityConfigurer.getVelocityEngine().getTemplate(viewName + ".vm");

        // 构造模版数据上下文
        VelocityContext context = new VelocityContext();
        context.put("res", res);
        if (null != req) {
            context.put("req", req);
        }

        // 生成绑定数据后结果
        StringWriter writer = new StringWriter();

        template.merge(context, writer);

        String mergedView = writer.toString();

        return mergedView;
    }
}
