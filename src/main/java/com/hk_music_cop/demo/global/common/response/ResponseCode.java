package com.hk_music_cop.demo.global.common.response;

import com.hk_music_cop.demo.global.common.message.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	// 공통 성공
	OK(200, "S001", "success.common.ok"),
	CREATED(201, "S002", "success.common.created"),
	DELETED(200, "S003", "success.common.deleted"),
	UPDATED(200, "S003", "success.common.updated"),
	OK_NO_MSG(200, "S001", null),
	CREATED_NO_MSG(201, "S002", null),
	DELETED_NO_MSG(200, "S003", null),
	UPDATED_NO_MSG(200, "S003", null),

	// 회원 관련 성공
	MEMBER_JOIN_SUCCESS(200, "SM001", "success.member.join"),
	MEMBER_UPDATE_SUCCESS(200, "SM002", "success.member.update"),
	MEMBER_DELETE_SUCCESS(200, "SM003", "success.member.delete"),

	// 인증 관련 성공
	LOGIN_SUCCESS(200, "SA001", "success.auth.login"),
	LOGOUT_SUCCESS(200, "SA002", "success.auth.logout"),
	TOKEN_REISSUE_SUCCESS(200, "SA003", "success.auth.token.reissue"),

	// Lottery 관련 성공
	LOTTERY_CREATE_SUCCESS(201, "SL001", "success.lottery.create"),
	LOTTERY_JOIN_SUCCESS(200, "SL002", "success.lottery.join"),
	LOTTERY_DELETE_SUCCESS(200, "SL003", "success.lottery.delete"),

	// Jandi 관련 성공
	JANDI_SCHEDULE_EMPTY(200, "SJ001", "success.jandi.schedule.empty");


	private final int status;
	private final String code;
	private final String message;

	public String getMessage() {
		return MessageSourceUtil.getMessage(this.message);
	}
}
