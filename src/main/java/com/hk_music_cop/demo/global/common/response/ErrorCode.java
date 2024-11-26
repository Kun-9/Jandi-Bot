package com.hk_music_cop.demo.global.common.response;

import com.hk_music_cop.demo.global.common.message.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// API 관련 에러
	API_ERROR(500, "E001", "error.api"),

	// 인증 관련 에러
	UNAUTHORIZED(403, "A001", "error.auth.unauthorized"),
	UNAUTHENTICATED(403, "A002", "error.auth.unauthenticated"),
	INCORRECT_PASSWORD(400, "A003", "error.auth.incorrect.password"),
	SECURITY_ERROR(403, "A004", "error.auth.security"),

	// 토큰 관련 에러
	EMPTY_TOKEN(401, "T001", "error.token.empty"),
	EXPIRED_REFRESH_TOKEN(401, "T002", "error.token.refresh.expired"),
	INVALID_TOKEN(401, "T003", "error.token.invalid"),
	TOKEN_EXPIRED(401, "T004", "error.token.expired"),

	// 회원 관련 에러
	UNKNOWN_MEMBER(404, "M001", "error.member.unknown"),
	USERNAME_NOT_FOUND(404, "M002", "error.member.username.notfound"),
	LOGIN_FAIL(400, "M003", "error.member.login.fail"),

	// 중복 관련 에러
	DUPLICATE_NAME(400, "D001", "error.duplicate.name"),
	DUPLICATE_USER_ID(400, "D002", "error.duplicate.userid"),

	// 롤(권한) 관련 에러
	UNKNOWN_ROLE(400, "R001", "error.role.unknown"),

	// 기능 관련 에러
	LOTTERY_NOT_FOUND(404, "L001", "error.lottery.notfound"),
	LOTTERY_EQUALS(404, "L002", "error.lottery.equals"),
	LOTTERY_DUPLICATE_NAME(400, "L003", "error.lottery.duplicate.name"),
	LOTTERY_POSITION_FORMAT(400, "L004", "error.lottery.position.format"),

	// 공통 에러
	UNKNOWN_ERROR(404, "C001", "error.unknown"),
	NOT_FOUND(404, "C001", "error.notfound"),
	UNDEFINED_COMMAND(400, "C002", "error.command.undefined"),
	INCORRECT_FORMAT(400, "C003", "error.format.incorrect"),

	CONVERTER_NOT_SUPPORT(400, "F001", "error.converter.notsupport"),

	// 데이터베이스 에러
	DATABASE_ERROR(500, "D001", "error.database"),
	DATABASE_DELETE_ERROR(500, "D002", "error.database.delete"),
	DATABASE_SELECT_ERROR(500, "D003", "error.database.select"),
	DATABASE_UPDATE_ERROR(500, "D004", "error.database.update"),
	DATABASE_CREATE_ERROR(500, "D005", "error.database.create");

	private final int status;
	private final String code;
	private final String message;

	public String getMessage() {
		return MessageSourceUtil.getMessage(this.message);
	}
}
