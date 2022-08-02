package com.example.jpa_test;

import com.alibaba.fastjson2.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class FastJsonUtil {

    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object, JSONWriter.Feature.WriteNulls);
    }

    public static JSONObject toObject(String text) {
        return JSON.parseObject(text, JSONReader.Feature.IgnoreSetNullValue);
    }

    public static <T> T toObject(String text, Class<T> objectClass) {
        return JSON.parseObject(text, objectClass, JSONReader.Feature.IgnoreSetNullValue);
    }

    public static <T> T toObject(String text, Type objectType) {
        return JSON.parseObject(text, objectType, JSONReader.Feature.IgnoreSetNullValue);
    }

    public static <T> T toObject(String text, TypeReference typeReference) {
        return JSON.parseObject(text, typeReference, JSONReader.Feature.IgnoreSetNullValue);
    }
}
