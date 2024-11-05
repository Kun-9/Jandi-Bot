package com.hk_music_cop.demo.jandi.dto.response;


import lombok.*;

@Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class JandiWebhookRequest {

	private String token;
	private String teamName;
	private String roomName;
	private Writer writer;
	private String text;
	private String data;
	private String keyword;
	private String createdAt;
	private String platform;
	private String ip;

	@Getter @ToString @NoArgsConstructor @AllArgsConstructor
	public static class Writer {
		private String id;
		private String name;
		private String email;
		private String phoneNumber;
	}
}
