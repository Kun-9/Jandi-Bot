package com.hk_music_cop.demo.global.security.jwt;

import com.hk_music_cop.demo.global.security.jwt.config.JwtProperties;
import com.hk_music_cop.demo.global.security.UserDetailService;
import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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

//	public String createToken(Authentication authentication) {
//
//		// 인증 정보를 UserDetails로 타입 캐스팅
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//		Instant now = Instant.now();
//		Instant validityTime = now.plus(Duration.ofSeconds(jwtProperties.tokenValidityInSeconds()));
//
//		return Jwts.builder()
//				.subject(userDetails.getUsername())
//				.issuedAt(Date.from(now))
//				.expiration(Date.from(validityTime))
//				.signWith(key)
//				.compact();
//	}

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

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();

		// 로그인 아이디로 유저 정보 가져오기
		UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(
				userDetails,
				"",
				userDetails.getAuthorities()
		);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private SecretKey getSignKey(String key) {
		byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(bytes);
	}
}
