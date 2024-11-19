package com.hk_music_cop.demo.global.security.jwt.repository;

public interface JwtTokenRepository {

	// 블랙리스트 추가
	void addBlacklist(String jti, String token, long remainTtl);

	// 블랙리스트에 있는지 검사
	boolean isBlacklisted(String jti, String accessToken);

	// 리프레시 토큰 추가
	void addRefreshToken(String jti, String token, long remainTtl);

	// 리프레시 토큰 검색
	boolean isPresentRefreshToken(String jti, String refreshToken);

	// 리프레시 토큰 삭제
	void deleteRefreshToken(String jti);
}
