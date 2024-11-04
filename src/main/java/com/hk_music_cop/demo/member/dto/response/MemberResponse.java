package com.hk_music_cop.demo.member.dto.response;

import lombok.*;

import java.time.LocalDateTime;


@Getter @ToString @AllArgsConstructor @NoArgsConstructor
public class MemberResponse {
	private Long memberId;
	private String userId;
	private String name;
	private String password;
	private LocalDateTime createdDate;
}
