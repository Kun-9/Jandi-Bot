package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.jandi.config.JandiProperties;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse.withoutConnectInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class JandiErrorResponseGeneratorImpl implements JandiErrorResponseGenerator {
	private final JandiProperties jandiProperties;


	public JandiWebhookResponse createCustomErrorResponse(CustomException e) {
		log.error("jandi custom Error : ", e);

		ErrorCode code = e.getErrorCode();

		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().failTitle(), jandiProperties.color().failColor());
		ConnectInfo connectInfo = new ConnectInfo(e.getMessage(), code.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}

	public JandiWebhookResponse createErrorResponse(Exception e, ErrorCode code) {
		log.error("jandi Error : ", e);

		JandiWebhookResponse response = withoutConnectInfo(jandiProperties.title().failTitle(), jandiProperties.color().failColor());
		ConnectInfo connectInfo = new ConnectInfo(code.getMessage(), code.getCode(), null);

		return response.withConnectInfo(connectInfo);
	}
}
