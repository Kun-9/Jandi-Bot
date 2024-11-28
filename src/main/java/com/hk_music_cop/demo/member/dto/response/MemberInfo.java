package com.hk_music_cop.demo.member.dto.response;

import java.util.List;

public record MemberInfo(String userId, String name, List<String> roles) {
	public static MemberInfo from(MemberResponse memberResponse) {
		return new MemberInfo(memberResponse.getUserId(), memberResponse.getName(), memberResponse.getRoles());
	}
}
