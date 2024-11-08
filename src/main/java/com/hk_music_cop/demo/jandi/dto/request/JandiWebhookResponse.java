package com.hk_music_cop.demo.jandi.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Getter @ToString
public class JandiWebhookResponse {
	private final String body;
	private final String connectColor;
	private final List<ConnectInfo> connectInfoList;

	public JandiWebhookResponse(String body, String connectColor, List<ConnectInfo> connectInfoList) {
		this.body = body;
		this.connectColor = connectColor;
		this.connectInfoList = connectInfoList;
	}

	public JandiWebhookResponse(String body, String connectColor, ConnectInfo connectInfo) {
		this(body, connectColor, Collections.singletonList(connectInfo));
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
