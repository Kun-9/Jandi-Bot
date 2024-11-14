package com.hk_music_cop.demo.member.dto.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;


@ToString @Getter
public class MemberSecurity {
	private final String userId;
	private final String password;
	private final boolean isEnabled;
	private final Collection<? extends GrantedAuthority> roles;

	private MemberSecurity(String userId, String password, Collection<? extends GrantedAuthority> roles, boolean isEnabled) {
		this.userId = userId;
		this.password = password;
		this.roles = roles;
		this.isEnabled = isEnabled;
	}

	public static MemberSecurity from(MemberResponse member) {
		List<SimpleGrantedAuthority> roleList = member.getRoles().stream()
				.map(SimpleGrantedAuthority::new)
				.toList();

		return new MemberSecurity(member.getUserId(), member.getPassword(), roleList, member.isEnabled());
	}


}
