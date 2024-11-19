package com.hk_music_cop.demo.global.jwt.domain;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomInvalidTokenException;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomTokenExpiredException;
import com.hk_music_cop.demo.global.jwt.config.JwtProperties;
import com.hk_music_cop.demo.global.security.service.UserDetailService;
import com.hk_music_cop.demo.global.jwt.dto.TokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	private final UserDetailService userDetailService;

	private SecretKey key;

	@PostConstruct
	protected void init() {
		// 환경변수로 제공받은 시크릿 키를 BASE64로 인코딩
		byte[] secretKey = jwtProperties.secret().getBytes();
		key = getSignKey(Base64.getEncoder().encodeToString(secretKey));
	}


	public TokenResponse createToken(Authentication authentication) {

		// 인증 정보를 UserDetails로 타입 캐스팅
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		Instant now = Instant.now();

		// accessToken 생성
		Instant validityTime = now.plus(Duration.ofSeconds(jwtProperties.accessTokenValidityInSeconds()));
		String accessToken = Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(now))
				.expiration(Date.from(validityTime))
				.signWith(key)
				.compact();

		// refresh token 생성
		Instant refreshValidityTime = now.plus(Duration.ofDays(jwtProperties.refreshTokenValidityInDays()));
		String refreshToken = Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(now))
				.expiration(Date.from(refreshValidityTime))
				.signWith(key)
				.compact();

		return TokenResponse.of(accessToken, refreshToken, Date.from(validityTime));
	}

	public Authentication getAuthenticationByToken(String token) {
		Claims claims = getClaims(token);
		return getAuthenticationByClaims(claims);
	}

	public Authentication getAuthenticationByClaims(Claims claims) {
		// 로그인 아이디로 유저 정보 가져오기
		UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(
				userDetails,
				"",
				userDetails.getAuthorities()
		);
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	// accessToken 검증
	public void validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token);
		} catch (ExpiredJwtException e) {
			throw new CustomTokenExpiredException("/auth/refresh 경로를 통해 갱신이 필요합니다.");
		} catch (SignatureException e) {
			throw new CustomInvalidTokenException("토큰 서명이 유효하지 않습니다.");
		} catch (MalformedJwtException e) {
			throw new CustomInvalidTokenException("올바르지 않은 토큰 형식입니다.");
		} catch (JwtException e) {
			throw new CustomInvalidTokenException();
		}
	}

	private SecretKey getSignKey(String key) {
		byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(bytes);
	}

}
