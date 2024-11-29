package com.hk_music_cop.demo.member.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class MemberResponse {
	private Long memberId;
	private String userId;
	private String name;
	private String password;
	private LocalDateTime createdDate;
	private List<String> roles;
	private boolean enabled;

	public MemberResponse(String userId, String password, List<String> roles) {
		this.userId = userId;
		this.password = password;
		this.roles = roles;
	}

	public MemberResponse withRoles(List<String> roles) {
		return new MemberResponse(this.memberId, this.userId, this.name, this.password, this.createdDate, roles, this.enabled);
	}

}
