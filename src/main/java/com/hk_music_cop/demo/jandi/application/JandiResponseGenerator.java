package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.config.JandiProperties;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse.withoutConnectInfo;

@Slf4j
@Component
@RequiredArgsConstructor
public class JandiResponseGenerator {

	private final JandiProperties jandiProperties;

	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, JandiWebhookResponse.ConnectInfo connectInfo) {
		JandiWebhookResponse response = withoutConnectInfo(responseCode.getMessage(), jandiProperties.color().successColor());

		return response.withConnectInfo(connectInfo);
	}

	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, List<JandiWebhookResponse.ConnectInfo> connectInfoList) {
		JandiWebhookResponse response = withoutConnectInfo(responseCode.getMessage(), jandiProperties.color().successColor());

		return response.withConnectInfoList(connectInfoList);
	}


	public JandiWebhookResponse createSuccessResponse(ResponseCode responseCode) {
		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().successTitle(), jandiProperties.color().successColor());
		JandiWebhookResponse.ConnectInfo connectInfo = new JandiWebhookResponse.ConnectInfo(responseCode.getMessage(), responseCode.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}


	public JandiWebhookResponse createCustomErrorResponse(CustomException e) {
		log.error("jandi custom Error : ", e);

		ResponseCode code = e.getResponseCode();

		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().failTitle(), jandiProperties.color().failColor());
		JandiWebhookResponse.ConnectInfo connectInfo = new JandiWebhookResponse.ConnectInfo(e.getMessage(), code.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}

	public JandiWebhookResponse createErrorResponse(Exception e, ResponseCode code) {
		log.error("jandi Error : ", e);

		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().failTitle(), jandiProperties.color().failColor());
		JandiWebhookResponse.ConnectInfo connectInfo = new JandiWebhookResponse.ConnectInfo(code.getMessage(), code.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}
}
