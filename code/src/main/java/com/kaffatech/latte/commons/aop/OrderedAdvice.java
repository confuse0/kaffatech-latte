package com.kaffatech.latte.commons.aop;

import org.springframework.core.Ordered;

/**
 * Created by lingzhen on 16/5/13.
 */
public abstract class OrderedAdvice implements Ordered {

    private int order;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
