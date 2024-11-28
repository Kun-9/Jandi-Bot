package com.hk_music_cop.demo.global.jwt.service;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomExpiredRefreshTokenException;
import com.hk_music_cop.demo.global.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;
import com.hk_music_cop.demo.member.dto.response.LoginResponse;

/**
 * JWT 토큰 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface JwtTokenService {

	/**
	 * 사용자 로그인을 처리하고 JWT 토큰을 발급합니다.
	 *
	 * @param loginRequest 로그인 요청 정보 (사용자 ID, 비밀번호)
	 * @return LoginResponse 유저 정보와 토큰정보가 포함된 응답 객체
	 * @throws CustomException 로그인 실패 시 발생 (잘못된 자격 증명)
	 */
	LoginResponse login(LoginRequest loginRequest);

	/**
	 * 리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.
	 *
	 * @param refreshToken 기존 리프레시 토큰
	 * @return TokenResponse 새로 발급된 액세스 토큰과 리프레시 토큰이 포함된 응답 객체
	 * @throws CustomExpiredRefreshTokenException 리프레시 토큰이 만료되었거나 유효하지 않은 경우
	 */
	TokenResponse refreshToken(String refreshToken);

	/**
	 * 사용자 로그아웃을 처리합니다.
	 * - 액세스 토큰을 블랙리스트에 추가
	 * - 리프레시 토큰을 삭제
	 *
	 * @param accessToken 현재 액세스 토큰
	 * @return boolean 로그아웃 성공 여부
	 */
	boolean logout(String accessToken);

	/**
	 * 주어진 액세스 토큰이 블랙리스트에 있는지 확인합니다.
	 *
	 * @param jti 토큰 식별자
	 * @param accessToken 검증할 액세스 토큰
	 * @throws CustomExpiredRefreshTokenException 토큰이 블랙리스트에 있는 경우
	 */
	void validateBlacklisted(String jti, String accessToken);
}
