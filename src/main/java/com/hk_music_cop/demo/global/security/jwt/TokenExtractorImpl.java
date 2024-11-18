package com.hk_music_cop.demo.global.security.jwt;

import com.hk_music_cop.demo.global.error.exceptions.CustomEmptyTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenExtractorImpl implements TokenExtractor {


	@Override
	public String extractAccessToken(HttpServletRequest request) {
		String tokenHeaderName = "Authorization";

		String accessToken = request.getHeader(tokenHeaderName);

		if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
			accessToken = accessToken.substring(7);
			return accessToken;
		}

		return null;
	}

	@Override
	public String extractRefreshToken(HttpServletRequest request) {
		String tokenHeaderName = "Refresh-Token";

		String refreshToken = request.getHeader(tokenHeaderName);

		if (StringUtils.hasText(refreshToken)) {
			return refreshToken;
		}

		return null;
	}
}
