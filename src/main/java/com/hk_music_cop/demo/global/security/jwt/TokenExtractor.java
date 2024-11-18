package com.hk_music_cop.demo.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenExtractor {

	// request에서 헤더의 Authorization값을 참조, 파싱하여 토큰값 추출
	String extractAccessTokenFromRequest(HttpServletRequest request);

	// request에서 헤더의 Refresh-Token값을 참조, 파싱하여 토큰값 추출
	String extractRefreshTokenFromRequest(HttpServletRequest request);
}
