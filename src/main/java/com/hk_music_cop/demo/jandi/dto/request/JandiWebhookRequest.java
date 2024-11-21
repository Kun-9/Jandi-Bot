package com.hk_music_cop.demo.jandi.dto.request;

import lombok.*;

@Value
public class JandiWebhookRequest implements JandiRequest {
	String token;
	String teamName;
	String roomName;
	Writer writer;
	String text;
	String data;
	String keyword;
	String createdAt;
	String platform;
	String ip;

	@Value
	public static class Writer implements JandiRequest.Writer {
		String id;
		String name;
		String email;
		String phoneNumber;
	}
}
