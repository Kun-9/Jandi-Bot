package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import org.json.JSONObject;

public interface JandiCommandService {

	JandiWebhookResponse executeCommand(JandiWebhookRequest request);
}
