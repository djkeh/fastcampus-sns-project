package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: 헤로쿠 환경에서 redis 를 쓰지 않기로 하여 map 으로 대체함
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

//    private final RedisTemplate<String, User> userRedisTemplate;
    private final Map<String, User> userRedisTemplate = new HashMap<>();
//    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(User user) {
        String key = getKey(user.getUsername());
        log.info("Set User to Redis {}, {}", key, user);
        userRedisTemplate.put(key, user);
//        userRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(String userName) {
        String key = getKey(userName);
        User user = userRedisTemplate.get(key);
//        User user = userRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {} , {}", key, user);
        return Optional.ofNullable(user);
    }

    private String getKey(String userName) {
        return "USER:" + userName;
    }

}
