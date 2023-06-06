package com.example.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
    public static String toJsonString(Object data) {
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
    public static <T> T fromJsonString(String jsonData, Class<T> beanType) throws JsonProcessingException {
        return MAPPER.readValue(jsonData, beanType);
    }

    /**
     * 将 JSON 字符串转换为指定类型的列表对象
     *
     * @param <T>        目标对象泛型
     * @param jsonString JSON 字符串
     * @param valueType  目标对象类型
     * @return 目标对象列表 list
     * @throws JsonProcessingException JSON 处理异常
     */
    public static <T> List<T> fromJsonList(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return MAPPER.readValue(jsonString, new TypeReference<List<T>>() {
        });
    }

    /**
     * 将 JSON 字符串转换为指定类型的 Set 对象
     *
     * @param <T>        目标对象泛型
     * @param jsonString JSON 字符串
     * @param valueType  目标对象类型
     * @return 目标对象 Set
     * @throws JsonProcessingException JSON 处理异常
     */
    public static <T> Set<T> fromJsonSet(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return MAPPER.readValue(jsonString, new TypeReference<Set<T>>() {
        });
    }

    /**
     * 将 JSON 字符串转换为指定类型的 Map 对象
     *
     * @param <K>        键类型泛型
     * @param <V>        值类型泛型
     * @param jsonString JSON 字符串
     * @param keyType    Map 键类型
     * @param valueType  Map 值类型
     * @return Map 对象
     * @throws JsonProcessingException JSON 处理异常
     */
    public static <K, V> Map<K, V> fromJsonMap(String jsonString, Class<K> keyType, Class<V> valueType) throws JsonProcessingException {
        return MAPPER.readValue(jsonString, new TypeReference<Map<K, V>>() {
        });
    }

}
