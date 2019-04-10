package com.sduwh.liutao.searchengine.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/10 08:43
 */

@Component
public class RedisUtils {

    private static boolean hasSerializerSet = false;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @SuppressWarnings("unchecked")
    private Jackson2JsonRedisSerializer genJsonSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<Object, Object> getRedis() {
        if (!hasSerializerSet) {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(genJsonSerializer());
            hasSerializerSet = true;
        }
        return this.redisTemplate;
    }
    
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
    
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }
    
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
    
    public long dncrement(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    
    public Map<Object, Object> hget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    public void hset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }
    
    public void hset(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
    }
    
    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }
    
    public void hdel(String key, Object[] item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    
    public double hincrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }
    
    public double hdncrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }
    
    public Set<Object> sget(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    
    public boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
    
    public long sget(String key, Object[] values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    
    public long sGetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }
    
    public long setRemove(String key, Object[] values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public List<Object> lget(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }
    
    public long lGetSize(String key) {
        return redisTemplate.opsForList().size(key);
    }
    
    public Object lget(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }
    
    public void ladd(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }
    
    public void lset(String key, List<Object> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }
    
    public void lset(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }
    
    public long ldel(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

}
