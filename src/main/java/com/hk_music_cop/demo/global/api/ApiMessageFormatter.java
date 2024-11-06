package com.hk_music_cop.demo.global.api;

import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface ApiMessageFormatter {

	HttpEntity<String> createResponseEntity(String webhookURL, JandiWebhookResponse JandiWebhookResponse);


	JSONObject createResponseMessage(JandiWebhookResponse jandiWebhookResponse);


	JandiWebhookResponse parseScheduleListToResponse(String title, String color, List<List<String>> result);
}
