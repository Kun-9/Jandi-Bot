package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;

public interface JandiRequstParser {
	void parseLotteryRequest(JandiWebhookRequest jandiWebhookRequest);

	void parseScheduleRequest(JandiWebhookRequest jandiWebhookRequest);

	void parseMemberRequest(JandiWebhookRequest jandiWebhookRequest);
}
