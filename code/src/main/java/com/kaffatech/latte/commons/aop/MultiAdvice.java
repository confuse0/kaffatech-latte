package com.kaffatech.latte.commons.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

/**
 * Created by lingzhen on 16/5/13.
 */
public class MultiAdvice implements MethodInterceptor {

    private List<MethodInterceptor> adviceChain;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (adviceChain == null || adviceChain.size() ==0) {
            throw new NullPointerException("adviceChain can not be null!");
        }
        return getRootMethodInvocation(invocation).proceed();
    }

    /**
     * 获取顶层方法执行器
     *
     * @param leaf
     * @return
     */
    private MethodInvocation getRootMethodInvocation(MethodInvocation leaf) {
        MethodInvocation root = leaf;
        for (int i = adviceChain.size() - 1; i >= 0; i--) {
            MethodInterceptor curAdvice = adviceChain.get(i);
            root = new LinkedMethodInvocation(root, curAdvice);

        }
        return root;
    }

    public void setAdviceChain(List<MethodInterceptor> adviceChain) {
        this.adviceChain = adviceChain;
    }
}
