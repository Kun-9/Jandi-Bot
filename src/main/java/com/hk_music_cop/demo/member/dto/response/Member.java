package com.hk_music_cop.demo.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter @ToString
public class Member {
	private final Long memberId;
	private final String userId;
	private final String name;
	private final String password;
	private final LocalDateTime createdDate;
	private final Collection<? extends GrantedAuthority> roles;

	private Member(Long memberId, String userId, String name, String password, LocalDateTime createdDate, Collection<? extends GrantedAuthority> roles) {
		this.memberId = memberId;
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.createdDate = createdDate;
		this.roles = roles;
	}

	public static Member from(MemberResponse memberResponse) {
		return ofSecurity(memberResponse.getUserId(), memberResponse.getPassword(), memberResponse.getRoles());
	}

	public static Member ofSecurity(String userId, String password, List<String> roles) {
		List<SimpleGrantedAuthority> roleList = roles.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();

		return new Member(null, userId, null, password, null, roleList);
	}

	public static Member ofResponse() {
		return new Member(null, null, null, null, null, null);
	}

	public static Member ofSignUp() {
		return new Member(null, null, null, null, null, null);
	}
}
