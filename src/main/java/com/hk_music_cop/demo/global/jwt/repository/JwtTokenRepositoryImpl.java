package com.hk_music_cop.demo.global.jwt.repository;

import com.hk_music_cop.demo.global.redis.RedisRepository;
import com.hk_music_cop.demo.global.jwt.common.TokenPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis를 의존한 토큰 Repository
 */
@RequiredArgsConstructor
@Service
public class JwtTokenRepositoryImpl implements JwtTokenRepository {

	private final RedisRepository redisRepository;

	// 엑세스 토큰만 블랙리스트에 등록한다.
	@Override
	public void addBlacklist(String jti, String accessToken, long remainTtl) {
		String key = TokenPrefix.BLACK_LIST.from(jti);

		redisRepository.setValueWithExpiration(key, accessToken, remainTtl, TimeUnit.SECONDS);
	}

	@Override
	public boolean isBlacklisted(String jti, String accessToken) {
		String key = TokenPrefix.BLACK_LIST.from(jti);

		return redisRepository.getValue(key)
				.filter(token -> token.equals(accessToken))
				.isPresent();
	}

	@Override
	public void addRefreshToken(String jti, String refreshToken, long remainTtl) {
		String key = TokenPrefix.REFRESH_TOKEN.from(jti);

		redisRepository.setValueWithExpiration(key, refreshToken, remainTtl, TimeUnit.DAYS);
	}

	@Override
	public boolean isPresentRefreshToken(String jti, String refreshToken) {
		String key = TokenPrefix.REFRESH_TOKEN.from(jti);
		Optional<Object> value = redisRepository.getValue(key);
		return value.isPresent() && value.get().equals(refreshToken);

	}

	@Override
	public void deleteRefreshToken(String jti) {
		String key = TokenPrefix.REFRESH_TOKEN.from(jti);
		redisRepository.deleteKey(key);
	}


}
