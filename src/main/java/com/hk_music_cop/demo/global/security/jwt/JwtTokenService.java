package com.hk_music_cop.demo.global.security.jwt;

import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;

public interface JwtTokenService {

	TokenResponse login(LoginRequest loginRequest);

	TokenResponse refreshToken(String refreshToken);

	boolean logout(String accessToken, String refreshToken);

	void validateBlacklisted(String jti, String accessToken);
}
