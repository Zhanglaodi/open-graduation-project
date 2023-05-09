package com.ctrl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Json utils.
 *
 * @author dalaodi
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成JSON数据
     *
     * @param data 对象
     * @return JSON数据 bean to json
     */
    public static String getBeanToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON数据转换成对象
     *
     * @param <T>      the type parameter
     * @param jsonData JSON数据
     * @param beanType 对象类型
     * @return 对象 json to bean
     * @throws JsonProcessingException the json processing exception
     */
    public static <T> T getJsonToBean(String jsonData, Class<T> beanType) throws JsonProcessingException {
        return MAPPER.readValue(jsonData, beanType);
    }

    /**
     * 将JSON数据转换成列表
     *
     * @param <T>      the type parameter
     * @param jsonData JSON数据
     * @param beanType 对象类型
     * @return 列表 json to list
     */
    public static <T> List<T> getJsonToList(String jsonData, Class<T> beanType) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON数据转换成Set集合
     *
     * @param <E>         the type parameter
     * @param jsonData    JSON数据
     * @param elementType 元素类型
     * @return Set集合 json to set
     */
    public static <E> Set<E> getJsonToSet(String jsonData, Class<E> elementType) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructCollectionType(Set.class, elementType);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON数据转换成Map集合
     *
     * @param <K>       the type parameter
     * @param <V>       the type parameter
     * @param jsonData  JSON数据
     * @param keyType   键类型
     * @param valueType 值类型
     * @return Map集合 json to map
     */
    public static <K, V> Map<K, V> getJsonToMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
