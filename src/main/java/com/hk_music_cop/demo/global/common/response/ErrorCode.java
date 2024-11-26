package com.hk_music_cop.demo.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// API 관련 에러
	API_ERROR(500, "E001", "API 호출 오류입니다."),

	// 인증 관련 에러
	UNAUTHORIZED(403, "A001", "권한이 없습니다."),
	UNAUTHENTICATED(403, "A002", "인증에 실패하였습니다."),
	INCORRECT_PASSWORD(400, "A003", "비밀번호가 일치하지 않습니다."),
	SECURITY_ERROR(403, "A004", "권한이 없거나 인증에 실패하였습니다."),

	// 토큰 관련 에러
	EMPTY_TOKEN(401, "T001", "토큰이 제공되지 않았습니다."),
	EXPIRED_REFRESH_TOKEN(401, "T002", "만료된 Token 입니다."),
	INVALID_TOKEN(401, "T003", "유효하지 않은 토큰 입니다."),
	TOKEN_EXPIRED(401, "T004", "토큰이 만료되었습니다."),

	// 회원 관련 에러
	UNKNOWN_MEMBER(404, "M001", "등록되지 않은 회원입니다."),
	USERNAME_NOT_FOUND(404, "M002", "유저 이름을 찾을 수 없습니다."),
	LOGIN_FAIL(400, "M003", "아이디 또는 비밀번호가 잘못되었습니다."),

	// 중복 관련 에러
	DUPLICATE_NAME(400, "D001", "이미 등록된 이름입니다."),
	DUPLICATE_USER_ID(400, "D002", "이미 존재하는 회원 아이디입니다."),

	// 롤(권한) 관련 에러
	UNKNOWN_ROLE(400, "R001", "없는 역할입니다. 관리자에게 문의해주세요."),

	// 기능 관련 에러
	LOTTERY_NOT_FOUND(404, "L001", "해당 lottery를 찾을 수 없습니다."),
	LOTTERY_EQUALS(404, "L002", "해당 lottery와 동일한 값입니다."),
	LOTTERY_DUPLICATE_NAME(400, "L003", "이미 등록된 lottery 이름입니다."),

	// 공통 에러
	UNKNOWN_ERROR(404, "C001", "알 수 없는 에러입니다."),
	NOT_FOUND(404, "C001", "해당 값이나 리소스를 찾지 못했습니다."),
	UNDEFINED_COMMAND(400, "C002", "적절하지 않은 명령어입니다."),
	INCORRECT_FORMAT(400, "C003", "날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요."),

	CONVERTER_NOT_SUPPORT(400, "F001", "컨버팅할 수 없는 타입입니다."),

	// 데이터베이스 에러
	DATABASE_ERROR(500, "D001", "데이터베이스 요청 과정에서 오류가 발생했습니다."),
	DATABASE_DELETE_ERROR(500, "D002", "데이터 삭제 중 오류가 발생했습니다."),
	DATABASE_SELECT_ERROR(500, "D003", "데이터 조회 중 오류가 발생했습니다."),
	DATABASE_UPDATE_ERROR(500, "D004", "데이터 수정 중 오류가 발생했습니다."),
	DATABASE_CREATE_ERROR(500, "D005", "데이터 생성 중 오류가 발생했습니다.");


	private final int status;
	private final String code;
	private final String message;
}