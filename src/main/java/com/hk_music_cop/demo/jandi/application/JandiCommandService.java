package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookRequest;

public interface JandiCommandService {

	JandiWebhookResponse executeCommand(JandiWebhookRequest request);
}

