package com.hk_music_cop.demo.external.jandi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter @ToString
public class JandiWebhookRequest {
	private String body;
	private String connectColor;
	private List<ConnectInfo> connectInfoList;

	public JandiWebhookRequest(String body, String connectColor) {
		this.body = body;
		this.connectColor = connectColor;
		this.connectInfoList = new ArrayList<>();
	}

	public void addConnectInfo(ConnectInfo info) {
		this.connectInfoList.add(info);
	}

	@Getter @Setter @ToString
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
