package com.hk_music_cop.demo.global.security.jwt.presentation;

import com.hk_music_cop.demo.ex.Token;
import com.hk_music_cop.demo.ex.TokenType;
import com.hk_music_cop.demo.global.security.jwt.JwtTokenService;
import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
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
	private final JwtTokenService jwtTokenService;

	@PostMapping("refresh")
	public ResponseEntity<TokenResponse> refreshToken(@Token(type = TokenType.REFRESH) String refreshToken) {
		TokenResponse tokenResponse = jwtTokenService.refreshToken(refreshToken);
		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}
}
