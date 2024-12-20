package com.hk_music_cop.demo.global.jwt.service;

import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomExpiredRefreshTokenException;
import com.hk_music_cop.demo.global.security.common.CustomUser;
import com.hk_music_cop.demo.global.jwt.domain.JwtTokenProvider;
import com.hk_music_cop.demo.global.jwt.config.JwtProperties;
import com.hk_music_cop.demo.global.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.global.jwt.repository.JwtTokenRepository;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;
import com.hk_music_cop.demo.member.dto.response.LoginResponse;
import com.hk_music_cop.demo.member.dto.response.MemberInfo;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenRepository jwtTokenRepository;
	private final JwtProperties jwtProperties;
	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authentication = getAuthenticationFromLogin(loginRequest);

		String userId = loginRequest.userId();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		TokenResponse tokenResponse = jwtTokenProvider.createToken(authentication);

		String refreshToken = tokenResponse.refreshToken();
		addRefreshToken(userId, refreshToken);

		MemberInfo memberInfo = MemberInfo.from(memberService.findMemberWithRoleByUserId(userId));

		return LoginResponse.of(memberInfo, tokenResponse);
	}

	private Authentication getAuthenticationFromLogin(LoginRequest loginRequest) {
		String userId = loginRequest.userId();
		String password = loginRequest.password();

		log.info("userId: {}, 로그인", userId);
		Authentication authenticate;

		try {
			authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userId, password));

		} catch (BadCredentialsException e) {
			throw new CustomException(ErrorCode.LOGIN_FAIL);
		}

		return authenticate;
	}

	@Override
	public TokenResponse refreshToken(String refreshToken) {
		// 토큰 기본 검증
		jwtTokenProvider.validateToken(refreshToken);

		// 인증 정보 파싱
		Authentication authentication = jwtTokenProvider.getAuthenticationByToken(refreshToken);

		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		String userId = customUser.getUsername();

		// Redis에 Refresh 토큰 존재 여부 확인
		validRefreshToken(refreshToken, userId);

		// 새로운 토큰 생성 (엑세스, 리프레시)
		TokenResponse tokenResponse = jwtTokenProvider.createToken(authentication);

		// Redis에 추가
		String newRefreshToken = tokenResponse.refreshToken();
		addRefreshToken(userId, newRefreshToken);

		return tokenResponse;
	}

	private void addRefreshToken(String jti, String refreshToken) {
		jwtTokenRepository.addRefreshToken(
				jti,
				refreshToken,
				jwtProperties.refreshTokenValidityInDays()
		);
	}

	private void validRefreshToken(String refreshToken, String userId) {
		// Redis에 존재하는지 확인
		if (!jwtTokenRepository.isPresentRefreshToken(userId, refreshToken)) {
			throw new CustomExpiredRefreshTokenException(refreshToken);
		}
	}

	@Override
	public boolean logout(String accessToken) {

		// 모든 토큰 검증
		jwtTokenProvider.validateToken(accessToken);

		// AccessToken jti 추출
		Claims accessTokenClaims = jwtTokenProvider.getClaims(accessToken);
		Authentication accessTokenAuthentication = jwtTokenProvider.getAuthenticationByClaims(accessTokenClaims);

		CustomUser customUser = (CustomUser) accessTokenAuthentication.getPrincipal();
		String userId = customUser.getUsername();

		// 해당 회원의 Refresh token 삭제
		jwtTokenRepository.deleteRefreshToken(userId);

		// 남은 시간 계산
		long remainTime = calculateRemainTime(accessTokenClaims);

		// 현재 AT를 REDIS 블랙리스트에 추가
		jwtTokenRepository.addBlacklist(userId, accessToken, remainTime);

		// REDIS에서 RT 삭제
		jwtTokenRepository.deleteRefreshToken(userId);

		log.info("'{}' 로그아웃", userId);

		return true;
	}

	private static long calculateRemainTime(Claims accessTokenClaims) {
		long expireTime = accessTokenClaims.getExpiration().getTime();
		long currentTime = System.currentTimeMillis();

		return Math.max(0, expireTime - currentTime);
	}

	public void validateBlacklisted(String jti, String accessToken) {
		if (jwtTokenRepository.isBlacklisted(jti, accessToken)) throw new CustomExpiredRefreshTokenException(accessToken);
	}
}
