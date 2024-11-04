package com.hk_music_cop.demo.external.jandi.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
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

	// Getters and setters

	@Getter @Setter @ToString
	public static class Writer {
		private String id;
		private String name;
		private String email;
		private String phoneNumber;

		// Getters and setters
	}
}
