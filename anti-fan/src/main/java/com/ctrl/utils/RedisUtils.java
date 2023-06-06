package com.ctrl.utils;

import com.example.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The type Redis utils.
 *
 * @author dalaodi
 */
@Component
public class RedisUtils {
    @Value("${spring.redis.database}")
    private Integer database;
    /**
     * The Redis template.
     */
    @Autowired
    @Qualifier("redis")
    RedisTemplate<String, Object> redisTemplate;

    /**
     * The constant NOT_EXPIRE.
     */
    public final static long NOT_EXPIRE = -1;

    /**
     * 设置数据库
     *
     * @param dbIndex 数据库索引
     */
    public void setDbIndex(Integer dbIndex) {
        if (dbIndex == null || dbIndex > 15 || dbIndex < 0) {
            dbIndex = database;
        }
        LettuceConnectionFactory redisConnectionFactory = (LettuceConnectionFactory) redisTemplate
                .getConnectionFactory();
        if (redisConnectionFactory == null) {
            return;
        }
        redisConnectionFactory.setDatabase(dbIndex);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 属性设置后
        redisConnectionFactory.afterPropertiesSet();
        // 重置连接
        redisConnectionFactory.resetConnection();
    }

    /**
     * 插入值-对象,指定数据库索引,指定过期时间
     *
     * @param key     键
     * @param value   值
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @param expire  过期时间 单位：秒
     */
    public void set(String key, Object value, Integer dbIndex, long expire) {
        // 选择数据库
        setDbIndex(dbIndex);
        redisTemplate.opsForValue().set(key, Objects.requireNonNull(JsonUtils.toJsonString(value)));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 插入值-对象
     *
     * @param key     键
     * @param value   值
     * @param dbIndex 数据库索引 范围 0-15 默认0
     */
    public void set(String key, Object value, Integer dbIndex) {
        set(key, value, dbIndex, NOT_EXPIRE);
    }

    /**
     * 插入值-对象 ,默认0 index数据库
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, 0, NOT_EXPIRE);
    }

    /**
     * 获取值-对象,指定数据库索引,并设置过期时间
     *
     * @param <T>     the type parameter
     * @param key     键
     * @param clazz   字节码对象
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @param expire  过期时间 单位：秒
     * @return t t
     * @throws JsonProcessingException the json processing exception
     */
    public <T> T get(String key, Class<T> clazz, Integer dbIndex, long expire) throws JsonProcessingException {
        setDbIndex(dbIndex);
        String value = (String) redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : JsonUtils.fromJsonString(value, clazz);
    }

    /**
     * 取值-对象 指定数据库索引
     *
     * @param <T>     the type parameter
     * @param key     键
     * @param clazz   字节码对象
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @return t t
     * @throws JsonProcessingException the json processing exception
     */
    public <T> T get(String key, Class<T> clazz, Integer dbIndex) throws JsonProcessingException {
        return get(key, clazz, dbIndex, NOT_EXPIRE);
    }

    /**
     * 取值-对象
     *
     * @param <T>   the type parameter
     * @param key   键
     * @param clazz 字节码对象
     * @return t t
     * @throws JsonProcessingException the json processing exception
     */
    public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
        return get(key, clazz, 0, NOT_EXPIRE);
    }

    /**
     * 获取值-字符串,指定数据库索引,设置过期时间
     *
     * @param key     键
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @param expire  过期时间 单位：秒
     * @return string string
     */
    public String get(String key, Integer dbIndex, long expire) {
        setDbIndex(dbIndex);
        String value = (String) redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 取值-字符串,指定数据库索引
     *
     * @param key     键
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @return string string
     */
    public String get(String key, Integer dbIndex) {
        return get(key, dbIndex, NOT_EXPIRE);
    }

    /**
     * 取值-字符串
     *
     * @param key 键
     * @return string string
     */
    public String get(String key) {
        return get(key, 0, NOT_EXPIRE);
    }

    /**
     * 删除 指定数据库索引
     *
     * @param key     键
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @return the boolean
     */
    public Boolean delete(String key, Integer dbIndex) {
        setDbIndex(dbIndex);
        return redisTemplate.delete(key);
    }

    /**
     * 删除
     *
     * @param key 键
     * @return the boolean
     */
    public Boolean delete(String key) {
        return delete(key, 0);
    }

    /**
     * 设置过期时间 ,指定数据库索引
     *
     * @param key     键
     * @param dbIndex 数据库索引 范围 0-15 默认0
     * @param expire  过期时间 单位：秒
     */
    public void expire(String key, Integer dbIndex, int expire) {
        setDbIndex(dbIndex);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置过期时间
     *
     * @param key    键
     * @param expire 过期时间 单位：秒
     */
    public void expire(String key, int expire) {
        expire(key, 0, expire);
    }

}
