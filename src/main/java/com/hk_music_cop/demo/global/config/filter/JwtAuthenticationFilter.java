package com.hk_music_cop.demo.global.config.filter;

import com.hk_music_cop.demo.global.error.ErrorHandler;
import com.hk_music_cop.demo.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final ErrorHandler errorHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
		try {
			Optional.ofNullable(getTokenFromRequest(request))
					.filter(tokenProvider::validateToken)
					.map(tokenProvider::getAuthentication)
					.ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			// 예외 처리
			String message = "인증되지 않은 사용자입니다.";

			errorHandler.handleFilterException(response, e, HttpStatus.UNAUTHORIZED, message);
		}

	}

	// request에서 헤더의 Authorization값을 참조, 파싱하여 토큰값 추출
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			bearerToken = bearerToken.substring(7);
			return bearerToken;
		}

		return null;
	}

}
