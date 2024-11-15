package com.hk_music_cop.demo.member.dto.response;

import com.hk_music_cop.demo.global.error.exceptions.CustomApiException;
import com.hk_music_cop.demo.global.error.exceptions.CustomNotFoundException;
import com.hk_music_cop.demo.global.error.exceptions.CustomUnknownMemberException;
import com.hk_music_cop.demo.global.security.Role;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;


@Slf4j
@Getter
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
				.map(role -> new SimpleGrantedAuthority(Role.getRole(role).name()))
				.toList();

		// 값 모두 들어왔는지 검증
		if (member.getUserId() == null || member.getPassword() == null || roleList.isEmpty()) {
			throw new CustomNotFoundException();
		}

		return new MemberSecurity(member.getUserId(), member.getPassword(), roleList, member.isEnabled());
	}
}
