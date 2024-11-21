package com.hk_music_cop.demo.jandi.dto.request;

import lombok.*;

@Value
public class JandiUserInfoRequest implements JandiRequest{
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
	public static class Writer implements JandiRequest.Writer{
		String id;
		String name;
		String email;
		String phoneNumber;
	}

	public static JandiUserInfoRequest from(JandiWebhookRequest webhookRequest) {
		return new JandiUserInfoRequest(
				webhookRequest.getToken(),
				webhookRequest.getTeamName(),
				webhookRequest.getRoomName(),
				new Writer(
						webhookRequest.getWriter().getId(),
						webhookRequest.getWriter().getName(),
						webhookRequest.getWriter().getEmail(),
						webhookRequest.getWriter().getPhoneNumber()
				),
				webhookRequest.getText(),
				webhookRequest.getData(),
				webhookRequest.getKeyword(),
				webhookRequest.getCreatedAt(),
				webhookRequest.getPlatform(),
				webhookRequest.getIp()
		);
	}

}
