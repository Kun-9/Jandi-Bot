package com.hk_music_cop.demo.jandi.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collections;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record JandiWebhookResponse(String body, String connectColor, List<ConnectInfo> connectInfo) {
	public static JandiWebhookResponse createResponseBase(String body, String connectColor) {
		return new JandiWebhookResponse(body, connectColor, null);
	}

	public JandiWebhookResponse withConnectInfoList(List<ConnectInfo> connectInfoList) {
		return new JandiWebhookResponse(this.body, this.connectColor, connectInfoList);
	}

	public JandiWebhookResponse withConnectInfo(ConnectInfo connectInfo) {
		return new JandiWebhookResponse(body, connectColor, Collections.singletonList(connectInfo));
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record ConnectInfo(String title, String description, String imageUrl) {}
}
