
package com.kaffatech.latte.mainframe.web.aop;

import com.kaffatech.latte.mainframe.blo.HeaderModelCreater;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

public class ControllerHeaderModelAdvice implements MethodInterceptor {

    private HeaderModelCreater headerModelCreater;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 执行具体操作
        Object result = invocation.proceed();

        Method method = invocation.getMethod();

        if (headerModelCreater != null && ModelAndView.class.equals(method.getReturnType())) {
            // 设置HEADER
            ((ModelAndView) result).getModel().put("header", headerModelCreater.create());
        }
        return result;
    }

    public void setHeaderModelCreater(HeaderModelCreater headerModelCreater) {
        this.headerModelCreater = headerModelCreater;
    }
}
