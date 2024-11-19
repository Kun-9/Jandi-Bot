package com.hk_music_cop.demo.global.security;

import com.hk_music_cop.demo.global.error.exceptions.CustomUnknownRoleException;

public enum Role {
	ROLE_ADMIN,
	ROLE_USER,
	ROLE_MANAGER;


	public static Role getRole(String roleValue) {
		try {
			return valueOf(roleValue);
		} catch (IllegalArgumentException e) {
			throw new CustomUnknownRoleException();
		}
	}

	public static String getRoleName(Role roleValue) {
		return roleValue.name().substring("ROLE_".length());
	}
}
