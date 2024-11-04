package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class JandiCommandService {

	JandiMessageFactory jandiMessageFactory;

	public JSONObject executeCommand(JandiWebhookRequest request) {
		return switch (request.getData()) {
			case "이번주 일정" -> jandiMessageFactory.scheduleWeekMessage(LocalDate.now());
			case "오늘 일정" -> jandiMessageFactory.scheduleDayMessage(LocalDate.now());
			case "랜덤 추첨" -> jandiMessageFactory.lotteryMessage(null);
			case "내 정보" -> jandiMessageFactory.infoMessage(request);
			default -> throw new JandiUndefinedCommand(request.getData());
		};
	}
}
