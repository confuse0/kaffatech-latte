package com.kaffatech.latte.commons.bean.transfer.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lingzhen on 16/11/2.
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transferred {

    String SELF = "SELF";

    String COPY = "COPY";

    String value() default SELF;

}

