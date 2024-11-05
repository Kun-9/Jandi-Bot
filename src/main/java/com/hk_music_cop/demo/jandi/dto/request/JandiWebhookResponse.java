package com.hk_music_cop.demo.jandi.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter @ToString
public class JandiWebhookResponse {
	private String body;
	private String connectColor;
	private List<ConnectInfo> connectInfoList;

	public JandiWebhookResponse(String body, String connectColor) {
		this.body = body;
		this.connectColor = connectColor;
		this.connectInfoList = new ArrayList<>();
	}

	public JandiWebhookResponse addConnectInfo(ConnectInfo info) {
		this.connectInfoList.add(info);
		return this;
	}

	@Getter @ToString @NoArgsConstructor
	public static class ConnectInfo {
		public ConnectInfo(String title, String description, String imageUrl) {
			this.title = title;
			this.description = description;
			this.imageUrl = imageUrl;
		}

		private String title;
		private String description;
		private String imageUrl;
	}
}
