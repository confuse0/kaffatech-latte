
package com.kaffatech.latte.ctx.base.aop;

import com.kaffatech.latte.ctx.base.servlet.RequestContextHolder;
import com.kaffatech.latte.ctx.base.servlet.ResponseContextHolder;
import com.kaffatech.latte.commons.net.http.util.CookieUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.commons.toolkit.uuid.UuidUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ControllerContextAdvice implements MethodInterceptor {

    /**
     * Cookie域
     */
    private String domain;

    /**
     * Cookie域过期时间(秒)
     */
    private int maxAge;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 设置Cookie参数
        processCookie();

        // 执行具体操作
        return invocation.proceed();
    }

    private void processCookie() {
        if (!StringUtils.isEmpty(domain)) {
            HttpServletResponse res = ResponseContextHolder.getResponse();
            if (res != null && StringUtils.isEmpty(RequestContextHolder.getClientToken())) {
                // 设置clientToken
                Cookie clientTokenCookie = CookieUtils.generateCookie(RequestContextHolder.CLIENT_TOKEN_KEY, UuidUtils.generate(), domain, maxAge);
                res.addCookie(clientTokenCookie);
            }
        }
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
