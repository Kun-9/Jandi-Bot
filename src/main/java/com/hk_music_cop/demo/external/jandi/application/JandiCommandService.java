package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class JandiCommandService {

	JandiMessageFactory jandiMessageFactory;

	public JSONObject executeCommand(JandiWebhookRequest request) {

		// 파싱 어떻게 해야할지 생각
		LotteryRequest lotteryRequest = new LotteryRequest(1L, "temp", "temp");
		Long targetId = 1L;

		return switch (request.getData()) {
			case "이번주 일정" -> jandiMessageFactory.scheduleWeekMessage(LocalDate.now());
			case "오늘 일정" -> jandiMessageFactory.scheduleDayMessage(LocalDate.now());
			case "랜덤 추첨" -> jandiMessageFactory.chooseLotteryMessage(null);
			case "내 정보" -> jandiMessageFactory.infoMessage(request);
			case "추첨 인원 추가" -> jandiMessageFactory.registerLotteryMessage(lotteryRequest);
			case "추첨 인원 삭제" -> jandiMessageFactory.deleteLotteryMessage(lotteryRequest);
			case "추첨 인원 수정" -> jandiMessageFactory.updateLotteryMessage(targetId, lotteryRequest);
			default -> throw new JandiUndefinedCommand(request.getData());
		};
	}
}
