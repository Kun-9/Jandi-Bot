package com.hk_music_cop.demo.jandi.dto.request;

public interface JandiRequest {
	String getToken();
	String getTeamName();
	String getRoomName();
	Writer getWriter();
	String getText();
	String getData();
	String getKeyword();
	String getCreatedAt();
	String getPlatform();
	String getIp();

	interface Writer {
		String getId();
		String getName();
		String getEmail();
		String getPhoneNumber();
	}
}
