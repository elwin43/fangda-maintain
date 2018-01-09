package com.fangda.maintain.web.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 *
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分隔符
     */
    private static String Separator = "|";
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getKeyString(String prefix, String key) throws RuntimeException {
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("prefix和key入参不能为空");
        }

        String redisKeyTemp = prefix + Separator + key;
        return redisKeyTemp;
    }

    /**
     * 获得HashSet对象
     *
     * @param key
     *            键值
     * @return Json String or String value
     */
    public <T> T getMemberSet(String prefix, String key, Class<T> clazz) throws RuntimeException {
        if (key == null || key.isEmpty())
            return null;

        String values = this.getMemberSet(prefix, key);
        T t = JSON.parseObject(values, clazz);
        return t;
    }

    /**
     * 获得HashSet对象
     *
     * @param key
     *            键值
     * @return Json String or String value
     */
    public String getMemberSet(String prefix, String key) throws RuntimeException {
        if (key == null || key.isEmpty())
            return null;

        String redisKey = getKeyString(prefix, key);
        Object values = redisTemplate.opsForValue().get(redisKey);
        return values == null ? null : (String) values;
    }
    /**
     * Member redis delete utils
     *
     * @param prefix
     * @param key
     */
    public void del(String prefix, String key) {
        String redisKey = getKeyString(prefix, key);
        redisTemplate.delete(redisKey);
    }

    /**
     * 设置Redis中的过期时间
     *
     * @param prefix
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(String prefix, String key, int seconds) {
        String redisKey = getKeyString(prefix, key);
        Boolean i = redisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
        return i;
    }

    /**
     * 设置缓存，加上失效时间
     *
     * @param key
     * @param value
     * @param seconds
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 By lihongfei
     */
    public boolean setMemberSet(String prefix, String key, Object value, int seconds) throws RuntimeException {
        boolean result = setMemberSet(prefix, key, value);
        if (result) {
            String redisKey = getKeyString(prefix, key);
            Boolean i = redisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            return i;
        }
        return false;
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 By lihongfei
     */
    public boolean setMemberSet(String prefix, String key, Object value) throws RuntimeException {
        if (value == null)
            return false;

        String redisKey = getKeyString(prefix, key);

        try {
            if (value instanceof String) {
                redisTemplate.opsForValue().set(redisKey, value.toString());
            } else {
                redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(value));
            }
            return true;
        } catch (Exception ex) {
            logger.error("setHSet error.", ex);
        }
        return false;
    }
}
