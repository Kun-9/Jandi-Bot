package com.hk_music_cop.demo.jandi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record JandiWebhookResponse(String body, String connectColor, List<ConnectInfo> connectInfo) {

	public static JandiWebhookResponse withoutConnectInfo(String body, String connectColor) {
		return new JandiWebhookResponse(body, connectColor, null);
	}

	public static JandiWebhookResponse of(String body, String connectColor, List<ConnectInfo> connectInfo) {
		return new JandiWebhookResponse(body, connectColor, connectInfo);
	}

	public JandiWebhookResponse withConnectInfoList(List<ConnectInfo> connectInfoList) {
		return new JandiWebhookResponse(this.body, this.connectColor, connectInfoList);
	}

	public JandiWebhookResponse withConnectInfo(ConnectInfo connectInfo) {
		return new JandiWebhookResponse(body, connectColor, Collections.singletonList(connectInfo));
	}
}
