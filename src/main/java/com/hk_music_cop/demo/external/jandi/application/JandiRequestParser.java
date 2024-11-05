package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.domain.JandiRequestData;
import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;

import static com.hk_music_cop.demo.external.jandi.domain.JandiRequestData.*;

public interface JandiRequestParser {
	void parseLotteryRequest(String requestData);

	Params getParameter(String requestData);

	void parseScheduleRequest(String requestData);

	void parseMemberRequest(String requestData);
}
