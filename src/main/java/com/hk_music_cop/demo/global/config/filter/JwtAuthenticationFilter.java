package com.hk_music_cop.demo.global.config.filter;

import com.hk_music_cop.demo.ex.ResponseCode;
import com.hk_music_cop.demo.global.error.ErrorHandler;
import com.hk_music_cop.demo.global.error.exceptions.CustomExpiredRefreshTokenException;
import com.hk_music_cop.demo.global.security.CustomUser;
import com.hk_music_cop.demo.global.security.jwt.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final ErrorHandler errorHandler;
	private final TokenExtractor tokenExtractor;
	private final JwtTokenRepository jwtTokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
		try {
			authenticateRequest(request);
			filterChain.doFilter(request, response);
		}
		catch (Exception e) {
		// 예외 처리
			handleAuthError(response, e);
		}
	}

	private void authenticateRequest(HttpServletRequest request) {
		Optional.ofNullable(tokenExtractor.extractAccessTokenFromRequest(request))
						.ifPresent(this::authenticateProcess);
	}

	private void authenticateProcess(String token) {
		tokenProvider.validateToken(token);

		Authentication authentication = tokenProvider.getAuthenticationByToken(token);

		CustomUser customUser = (CustomUser) authentication.getPrincipal();

		// 블랙리스트인지 확인
		if(jwtTokenRepository.isBlacklisted(customUser.getUsername(), token))
			throw new CustomExpiredRefreshTokenException();

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void handleAuthError(HttpServletResponse response, Exception e) throws IOException {
		// 사용자에게 권한없음 오류 반환
		errorHandler.handleExceptionDirect(response, e, ResponseCode.UNAUTHORIZED);
	}
}
