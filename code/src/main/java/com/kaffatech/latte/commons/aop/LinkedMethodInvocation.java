package com.kaffatech.latte.commons.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * Created by lingzhen on 16/5/13.
 */
public class LinkedMethodInvocation implements MethodInvocation {

    /**
     * 当前通知拦截器
     */
    private MethodInterceptor advice;

    /**
     * 下一层被代理方法执行器
     */
    private MethodInvocation delegate;

    public LinkedMethodInvocation(MethodInvocation delegate, MethodInterceptor advice) {
        this.delegate = delegate;
        this.advice = advice;
    }

    @Override
    public Method getMethod() {
        return delegate.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return delegate.getArguments();
    }

    @Override
    public Object proceed() throws Throwable {
        if (advice != null) {
            return advice.invoke(delegate);
        }

        return delegate.proceed();
    }

    @Override
    public Object getThis() {
        return delegate.getThis();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return delegate.getStaticPart();
    }
}
