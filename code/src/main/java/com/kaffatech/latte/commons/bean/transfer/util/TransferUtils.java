
package com.kaffatech.latte.commons.bean.transfer.util;

import com.kaffatech.latte.commons.bean.model.BaseBean;
import com.kaffatech.latte.security.model.annotation.Decrypt;
import com.kaffatech.latte.security.model.annotation.Encrypt;
import com.kaffatech.latte.commons.bean.transfer.model.annotation.Escape;
import com.kaffatech.latte.commons.bean.transfer.model.annotation.Transferred;
import com.kaffatech.latte.commons.bean.util.BeanUtils;
import com.kaffatech.latte.security.util.KeyUtils;
import com.kaffatech.latte.commons.toolkit.base.ArrayUtils;
import com.kaffatech.latte.security.util.RsaUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhen.ling
 */
public class TransferUtils {

    public static <T> T transfer(T obj) {
        Transferred ann = getAnnotaion(obj);
        if (ann != null) {
            if (Transferred.COPY.equals(ann.value())) {
                // 先拷贝再转换
                T copyObj = (T) BeanUtils.convert(obj, obj.getClass());
                transferObj(copyObj);
                return copyObj;
            } else {
                // 直接转换
                transferObj(obj);
                return obj;
            }
        }

        return obj;
    }

    private static void transferObj(Object obj) {
        try {
            if (obj != null) {
                PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(obj.getClass());

                for (PropertyDescriptor prop : props) {
                    Method readMethod = prop.getReadMethod();
                    Class<?> propType = readMethod.getReturnType();
                    if (String.class.isAssignableFrom(propType)) {
                        String propValue = (String) readMethod.invoke(obj);

                        prop.getWriteMethod().invoke(obj, transferString(readMethod, propValue));
                    } else if (List.class.isAssignableFrom(propType)) {
                        // List类型
                        List<?> propValue = (List<?>) readMethod.invoke(obj);
                        if (propValue != null) {
                            for (Object each : propValue) {
                                transferObj(each);
                            }
                        }
                    } else if (Set.class.isAssignableFrom(propType)) {
                        // Set类型
                        Set<?> propValue = (Set<?>) readMethod.invoke(obj);
                        if (propValue != null) {
                            for (Object each : propValue) {
                                transferObj(each);
                            }
                        }
                    } else if (Map.class.isAssignableFrom(propType)) {
                        // Map类型
                        Map<Object, Object> propValue = (Map) readMethod.invoke(obj);
                        transferMap(propValue);
                    } else if (BaseBean.class.isAssignableFrom(propType)) {
                        // 复合类型
                        Object propValue = readMethod.invoke(obj);
                        if (propValue != null) {
                            transferObj(propValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void transferMap(Map<Object, Object> obj) {
        try {
            if (obj != null) {
                for (Map.Entry<Object, Object> entry : obj.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (value != null) {
                        Class<?> propType = entry.getValue().getClass();
                        if (String.class.isAssignableFrom(propType)) {
                            // Map类型由于没有注解信息所以不需要做改变
                        } else if (List.class.isAssignableFrom(propType)) {
                            // List类型
                            List<?> propValue = (List<?>) value;
                            if (propValue != null) {
                                for (Object each : propValue) {
                                    transferObj(each);
                                }
                            }
                        } else if (Set.class.isAssignableFrom(propType)) {
                            // Set类型
                            Set<?> propValue = (Set<?>) value;
                            if (propValue != null) {
                                for (Object each : propValue) {
                                    transferObj(each);
                                }
                            }
                        } else if (Map.class.isAssignableFrom(propType)) {
                            // Map类型
                            Map<Object, Object> propValue = (Map) value;
                            transferMap(propValue);
                        } else if (BaseBean.class.isAssignableFrom(propType)) {
                            // 复合类型
                            Object propValue = value;
                            if (propValue != null) {
                                transferObj(propValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String transferString(Method readMethod, String propValue) {
        String trasferStr = propValue;
        if (StringUtils.isEmpty(trasferStr)) {
            return trasferStr;
        }

        // 判断是否需要RSA解密
        Encrypt encryptedAnn = readMethod.getAnnotation(Encrypt.class);
        if (encryptedAnn != null && encryptedAnn.algorithm().equals("RSA")) {
            trasferStr = RsaUtils.decryptByPrivateKey(KeyUtils.getCryptPrivateKey(), propValue);
        }

        // 判断是否需要RSA加密
        Decrypt decryptedAnn = readMethod.getAnnotation(Decrypt.class);
        if (decryptedAnn != null && decryptedAnn.algorithm().equals("RSA")) {
            trasferStr = RsaUtils.encryptByPublicKey(KeyUtils.getCryptPublicKey(), propValue);
        }

        // 判断是否需要HTML转义
        Escape escapeAnn = readMethod.getAnnotation(Escape.class);
        if (escapeAnn != null && escapeAnn.value().equals("HTML")) {
            trasferStr = EscapeHtmlUtils.escapeHtml(trasferStr);
        }
        return trasferStr;
    }

    private static Transferred getAnnotaion(Object arg) {
        if (arg == null) {
            return null;
        }

        Annotation[] anns = arg.getClass().getAnnotations();
        if (ArrayUtils.isEmpty(anns)) {
            return null;
        }

        for (Annotation each : anns) {
            if (each instanceof Transferred) {
                // 如果存在
                return (Transferred) each;
            }
        }
        return null;
    }

}
