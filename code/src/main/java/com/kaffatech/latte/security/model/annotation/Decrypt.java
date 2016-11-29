
package com.kaffatech.latte.security.model.annotation;

import com.kaffatech.latte.security.util.DesedeUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhen.ling
 *
 */

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Decrypt {

	String algorithm() default DesedeUtils.ALGORITHM;
}
