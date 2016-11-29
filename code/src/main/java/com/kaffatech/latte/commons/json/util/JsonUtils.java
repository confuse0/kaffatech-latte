
package com.kaffatech.latte.commons.json.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhen.ling
 */
public class JsonUtils {

    public static String toJsonString(Object obj) {
        String jsonStr = null;
        if (obj == null) {
            return jsonStr;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return jsonStr;
    }

    public static String toNonNullJsonString(Object obj) {
        String jsonStr = null;
        if (obj == null) {
            return jsonStr;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);

        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return jsonStr;
    }

    public static <T> T toJsonObject(String jsonStr, Class<T> clazz) {
        T obj = null;
        if (StringUtils.isEmpty(jsonStr)) {
            return obj;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            obj = mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return obj;
    }

    /**
     * 将jsonA数组转换成obj List
     *
     * @param clazz
     * @return
     */
    public static <T> List<T> convertJsonArrayToList(String jsonStr,
                                                     Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            if (!StringUtils.isEmpty(jsonStr)) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String json = jsonArray.get(i).toString();
                    T obj = JsonUtils.toJsonObject(json, clazz);
                    list.add(obj);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        Map<String, String> m = new HashMap<String, String>();
        String s = toJsonString(m);
        System.out.println(s);
        System.out.println(toJsonObject(s, Map.class));
    }
}
