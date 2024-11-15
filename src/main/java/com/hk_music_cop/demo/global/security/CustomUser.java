package com.hk_music_cop.demo.global.security;

import com.hk_music_cop.demo.member.dto.response.MemberSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public static CustomUser from(MemberSecurity memberSecurity) {
		return new CustomUser(memberSecurity.getUserId(), memberSecurity.getPassword(), memberSecurity.getRoles());
	}
}
