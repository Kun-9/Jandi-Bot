package com.hk_music_cop.demo.global.security.jwt.presentation;

import com.hk_music_cop.demo.global.security.jwt.JwtTokenProvider;
import com.hk_music_cop.demo.global.security.jwt.JwtTokenService;
import com.hk_music_cop.demo.global.security.jwt.TokenExtractor;
import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class JwtController {
	private final TokenExtractor tokenExtractor;
	private final JwtTokenService jwtTokenService;

	@PostMapping("refresh")
	public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request) {
		String refreshToken = tokenExtractor.extractRefreshTokenFromRequest(request);

		TokenResponse tokenResponse = jwtTokenService.refreshToken(refreshToken);

		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}
}
