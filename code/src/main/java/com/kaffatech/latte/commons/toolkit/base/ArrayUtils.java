package com.kaffatech.latte.commons.toolkit.base;

import java.util.Arrays;
import java.util.List;

/**
 * @author lingzhen on 16/8/30.
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(T... item) {
        return Arrays.asList(item);
    }

}
