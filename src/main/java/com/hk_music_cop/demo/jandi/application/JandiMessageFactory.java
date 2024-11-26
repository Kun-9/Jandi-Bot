package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;

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

	JandiWebhookResponse lotteryListMessage(List<LotteryDetailResponse> lotteryDetailResponseList);
}
