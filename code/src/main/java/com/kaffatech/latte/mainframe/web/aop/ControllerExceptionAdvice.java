
package com.kaffatech.latte.mainframe.web.aop;

import com.kaffatech.latte.ctx.base.servlet.RequestContextHolder;
import com.kaffatech.latte.ctx.base.servlet.SessionContextHolder;
import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.mainframe.model.type.BaseResultCode;
import com.kaffatech.latte.mainframe.model.exception.BizException;
import com.kaffatech.latte.mainframe.dto.Response;
import com.kaffatech.latte.mainframe.dto.util.ResponseUtils;
import com.kaffatech.latte.commons.log.Digest;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;
import com.kaffatech.latte.mainframe.web.spring.web.util.ModelAndViewUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerExceptionAdvice implements MethodInterceptor {

    /**
     * 错误跳转页面
     */
    private String errorView;

    /**
     * 没有数据的跳转页面
     */
    private String noDataView;

    /**
     * 登录跳转页面
     */
    private String loginView;

    /**
     * 存储在SESSION中的用户数据KEY
     */
    private String userKey;

    /**
     * 不需要登录校验的方法
     */
    private Set<String> notNeedLoginSet;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = null;

        Method method = invocation.getMethod();

        try {
            if (Response.class.isAssignableFrom(method.getReturnType())) {
                result = processResponseDto(invocation);
            } else if (ModelAndView.class.equals(method.getReturnType())) {
                result = processModelAndView(invocation);
            } else {
                result = invocation.proceed();
            }
        } finally {
            Digest digest = new Digest();
            digest.setUrl(RequestContextHolder.getUrl());
            digest.setOp(getControllerName(method) + "." + method.getName());
            digest.setReq(Arrays.asList(invocation.getArguments()));
            digest.setRes(result);
            digest.setCost(System.currentTimeMillis() - startTime);

            LogUtils.info(Log.CTRL_DIGEST_LOGGER, digest);
        }
        return result;
    }

    /**
     * 处理AJAX请求
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object processResponseDto(MethodInvocation invocation) throws Throwable {
        Object result = null;

        try {
            result = doMethod(invocation);
        } catch (BizException e) {
            LogUtils.warn(Log.WARN_LOGGER, e);

            if (BaseResultCode.NEED_LOGIN.getCode().equals(
                    e.getCode())) {
                result = ResponseUtils.createLoginErrorRes(getLoginUrl(true));
            } else {
                result = ResponseUtils.create(e);
            }
        } catch (Throwable e) {
            LogUtils.error(Log.ERROR_LOGGER, e);

            result = ResponseUtils.createSysError();
        }
        return result;
    }

    /**
     * 处理普通页面请求
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object processModelAndView(MethodInvocation invocation) throws Throwable {
        Object result = null;

        try {
            result = doMethod(invocation);
        } catch (BizException e) {
            LogUtils.warn(Log.WARN_LOGGER, e);

            Map<String, Object> m = new HashMap<String, Object>();
            m.put("code", e.getCode());
            m.put("message", e.getMessage());

            if (BaseResultCode.NEED_LOGIN.getCode().equals(
                    e.getCode())) {
                return ModelAndViewUtils.createForRedirect(getLoginUrl(false));
            } else {
                result = ModelAndViewUtils.create(errorView, m);
            }
        } catch (Throwable e) {
            LogUtils.error(Log.ERROR_LOGGER, e);

            Map<String, Object> m = new HashMap<String, Object>();
            m.put("code", BaseResultCode.SYS_ERROR.getCode());
            m.put("message", BaseResultCode.SYS_ERROR.getMessage());

            result = ModelAndViewUtils.create(errorView, m);
        }
        return result;
    }

    private String getLoginUrl(boolean isAjax) {
        String loginUrl = loginView;

        String redirectUrl = null;
        if (isAjax) {
            // ajax请求需要跳回到上一个页面
            redirectUrl = RequestContextHolder.getReferUrl();
        } else {
            redirectUrl = RequestContextHolder.getUrl();
        }

        if (!StringUtils.isEmpty(redirectUrl)) {
            loginUrl = loginView + "?redirectUrl=" + redirectUrl;
        }
        return loginUrl;
    }

    /**
     * 执行方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object doMethod(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String operation = method.getDeclaringClass().getName() + "."
                + method.getName();
        if (needLoginCheck(operation)) {
            if (SessionContextHolder.get(userKey) == null) {
                throw new BizException(BaseResultCode.NEED_LOGIN);
            }
        }
        return invocation.proceed();
    }

    /**
     * 判断是否需要登录校验
     *
     * @param operation
     * @return
     */
    private boolean needLoginCheck(String operation) {
        boolean needLoginCheck = true;
        if (notNeedLoginSet == null) {
            return false;
        }

        if (SystemProperties.isUnitTest()) {
            needLoginCheck = false;
        } else {
            for (String each : notNeedLoginSet) {
                Pattern pattern = Pattern.compile(each.trim());
                Matcher matcher = pattern.matcher(operation);
                if (matcher.matches()) {
                    needLoginCheck = false;
                    break;
                }
            }
        }
        return needLoginCheck;
    }

    /**
     * 获取控制其名称
     *
     * @param method
     * @return
     */
    private String getControllerName(Method method) {
        String[] array = method.getDeclaringClass().getName().split("\\.");
        return array[array.length - 1];
    }

    public void setErrorView(String errorView) {
        this.errorView = errorView;
    }

    public void setNoDataView(String noDataView) {
        this.noDataView = noDataView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public void setNotNeedLoginSet(Set<String> notNeedLoginSet) {
        this.notNeedLoginSet = notNeedLoginSet;
    }
}
