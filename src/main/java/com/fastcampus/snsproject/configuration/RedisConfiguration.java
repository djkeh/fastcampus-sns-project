package com.fastcampus.snsproject.configuration;

import com.fastcampus.snsproject.model.User;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfiguration {

  @Value("${spring.redis.port}")
  public int port;

  @Value("${spring.redis.host}")
  public String host;

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
//    RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
//    org.springframework.data.redis.connection.RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
//    factory.afterPropertiesSet();
//    return factory;
  }


  @Bean
  public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    return redisTemplate;
  }

}
