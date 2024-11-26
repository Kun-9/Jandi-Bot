package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.config.JandiProperties;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse.withoutConnectInfo;

@Slf4j
@Component
@RequiredArgsConstructor
public class JandiSuccessResponseGeneratorImpl implements JandiSuccessResponseGenerator {

	private final JandiProperties jandiProperties;

	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, ConnectInfo connectInfo) {
		JandiWebhookResponse response = withoutConnectInfo(responseCode.getMessage(), jandiProperties.color().successColor());

		return response.withConnectInfo(connectInfo);
	}

	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, List<ConnectInfo> connectInfoList) {
		JandiWebhookResponse response = withoutConnectInfo(responseCode.getMessage(), jandiProperties.color().successColor());

		return response.withConnectInfoList(connectInfoList);
	}


	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode) {
		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().successTitle(), jandiProperties.color().successColor());
		ConnectInfo connectInfo = new ConnectInfo(responseCode.getMessage(), responseCode.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}
}
