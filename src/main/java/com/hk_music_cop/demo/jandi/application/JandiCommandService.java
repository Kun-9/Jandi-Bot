package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import org.json.JSONObject;

public interface JandiCommandService {

	JSONObject executeCommand(JandiWebhookRequest request);
}
