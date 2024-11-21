package com.hk_music_cop.demo.jandi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConnectInfo(String title, String description, String imageUrl) {

	public static ConnectInfo of(String title, String description, String imageUrl) {
		return new ConnectInfo(title, description, imageUrl);
	}

	public static ConnectInfo fromDescription(String description) {
		return new ConnectInfo(null, description, null);
	}

	public static ConnectInfo withOutImg(String title, String description) {
		return new ConnectInfo(title, description, null);
	}
}