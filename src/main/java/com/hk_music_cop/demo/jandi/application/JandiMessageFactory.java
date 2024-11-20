package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public interface JandiMessageFactory {
	JandiWebhookResponse scheduleWeekMessage(LocalDate date);

	JandiWebhookResponse scheduleDayMessage(LocalDate date);

	JandiWebhookResponse chooseLotteryMessage(String imgURL);

	JandiWebhookResponse infoMessage(JandiWebhookRequest jandiWebhookRequest);

	JandiWebhookResponse registerLotteryMessage(LotteryRequest lotteryRequest);

	JandiWebhookResponse deleteLotteryMessage(LotteryRequest lotteryRequest);

	JandiWebhookResponse updateLotteryMessage(Long memberId, LotteryUpdateRequest request);

	JandiWebhookResponse lotteryListMessage(List<LotteryResponse> lotteryResponseList);
}
