package com.hk_music_cop.demo.member.dto.request;

import lombok.*;

@AllArgsConstructor
@Getter @Setter @ToString
public class MemberRequest {
	private String name;
	private String userId;
	private String password;
}
