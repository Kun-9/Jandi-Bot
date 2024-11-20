package com.hk_music_cop.demo.jandi.dto.request;

import lombok.*;

import java.util.Collections;
import java.util.List;


public record JandiWebhookResponse(String body, String connectColor, List<ConnectInfo> connectInfoList) {
	public static JandiWebhookResponse createResponseBase(String body, String connectColor) {
		return new JandiWebhookResponse(body, connectColor, null);
	}

	public JandiWebhookResponse withConnectInfoList(List<ConnectInfo> connectInfoList) {
		return new JandiWebhookResponse(this.body, this.connectColor, connectInfoList);
	}

	public JandiWebhookResponse withConnectInfo(ConnectInfo connectInfo) {
		return new JandiWebhookResponse(body, connectColor, Collections.singletonList(connectInfo));
	}


	@Getter
	@ToString
	@NoArgsConstructor
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
