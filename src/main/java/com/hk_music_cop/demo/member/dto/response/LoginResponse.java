package com.hk_music_cop.demo.member.dto.response;

import com.hk_music_cop.demo.global.jwt.dto.TokenResponse;

import java.time.LocalDateTime;
import java.util.List;

public record LoginResponse(MemberInfo memberInfo, TokenResponse token) {
	public static LoginResponse of(MemberInfo memberInfo, TokenResponse token) {
		return new LoginResponse(memberInfo, token);
	}
}
