package com.hk_music_cop.demo.global.security.jwt.common;

public enum TokenPrefix {
	REFRESH_TOKEN("RT:"),
	BLACK_LIST("BL:"),
	ACCESS_TOKEN("AT:");

	private final String prefix;

	TokenPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String from(String key) {
		return prefix + key;
	}
}
