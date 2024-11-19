package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.ex.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public interface JandiMessageFactory {
	JSONObject scheduleWeekMessage(LocalDate date);

	JSONObject scheduleDayMessage(LocalDate date);

	JSONObject chooseLotteryMessage(String imgURL);

	JSONObject errorMessage(ResponseCode message);

	JSONObject infoMessage(JandiWebhookRequest jandiWebhookRequest);

	JSONObject registerLotteryMessage(LotteryRequest lotteryRequest);

	JSONObject deleteLotteryMessage(LotteryRequest lotteryRequest);

	JSONObject updateLotteryMessage(Long memberId, LotteryUpdateRequest request);

	JSONObject lotteryListMessage(List<LotteryResponse> lotteryResponseList);
}
