
package com.kaffatech.latte.commons.bean.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.kaffatech.latte.commons.json.util.JsonUtils;

/**
 * @author zhen.ling
 */
public abstract class BaseBean implements Serializable {

    /**
     * 版本序列号
     */
    private static final long serialVersionUID = 1L;

    public static <T> T fromString(String str, Class<T> clazz) {
        return JsonUtils.toJsonObject(str, clazz);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String s = null;
        try {
            s = JsonUtils.toNonNullJsonString(this);
        } catch (Exception e) {
            s = ToStringBuilder.reflectionToString(this,
                    ToStringStyle.SHORT_PREFIX_STYLE);
        }
        return s;
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public int hashcode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
