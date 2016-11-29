package com.kaffatech.latte.commons.bean.util;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author lingzhen on 16/8/25.
 */
public class EnhanceUtils {

    public static <T> T create(Class<T> superClass, MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(superClass);
        // 设置拦截器
        enhancer.setCallback(methodInterceptor);

        //通过字节码技术动态创建子类实例
        return (T) enhancer.create();
    }
}
