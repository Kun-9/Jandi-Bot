package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import org.json.JSONObject;

import java.time.LocalDate;

public interface JandiMessageFactory {
	JSONObject scheduleWeekMessage(LocalDate date);

	JSONObject scheduleDayMessage(LocalDate date);

	JSONObject lotteryMessage(String imgURL);

	JSONObject errorMessage(String message);

	JSONObject infoMessage(JandiWebhookRequest jandiWebhookRequest);
}
