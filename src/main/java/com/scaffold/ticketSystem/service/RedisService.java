package com.scaffold.ticketSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveObjectToCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getObjectFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void clearCacheByKey(String key){
        redisTemplate.delete(key);
    }
}
