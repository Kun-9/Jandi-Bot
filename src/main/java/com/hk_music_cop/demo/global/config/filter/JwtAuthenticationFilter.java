package com.hk_music_cop.demo.global.config.filter;

import com.hk_music_cop.demo.global.error.ErrorHandler;
import com.hk_music_cop.demo.global.security.jwt.JwtTokenProvider;
import com.hk_music_cop.demo.global.security.jwt.TokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
		Optional.ofNullable(tokenExtractor.extractAccessToken(request))
						.ifPresent(this::authenticateProcess);
	}

	private void authenticateProcess(String token) {
		tokenProvider.validateToken(token);
		Authentication authentication = tokenProvider.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void handleAuthError(HttpServletResponse response, Exception e) throws IOException {
		errorHandler.handleFilterException(response, e, HttpStatus.UNAUTHORIZED);
	}
}
