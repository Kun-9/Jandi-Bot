package com.hk_music_cop.demo.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
	private final RedisTemplate<String, Object> redisTemplate;

	// 값 저장
	public void setValue(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	// 값 조회
	public Optional<Object> getValue(String key) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key));
	}

	public void setValueWithExpiration(String key, String value, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

	public void setHash(String key, String field, String value) {
		redisTemplate.opsForHash().put(key, field, value);
	}

}