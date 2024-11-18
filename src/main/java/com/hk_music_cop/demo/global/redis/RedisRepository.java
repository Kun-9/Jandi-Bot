package com.hk_music_cop.demo.global.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisRepository {
	// 값 저장
	void setValue(String key, String value);
	// 값 조회
	Optional<Object> getValue(String key);

	void setValueWithExpiration(String key, String value, long timeout, TimeUnit timeUnit);

	void deleteKey(String key);

	void setHash(String key, String field, String value);
}
