package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.request.JandiUserInfoRequest;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookRequest;
import com.hk_music_cop.demo.jandi.util.converter.connectInfo.ConnectInfoConverterComposite;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryView;
import com.hk_music_cop.demo.lottery.dto.response.LotteryViewList;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JandiMessageFactoryImpl implements JandiMessageFactory {

	private final JandiResponseGenerator jandiResponseGenerator;
	private final ScheduleService scheduleService;
	private final LotteryService lotteryService;
	private final ConnectInfoConverterComposite connectInfoConverter;

	@Override
	public JandiWebhookResponse scheduleWeekMessage(LocalDate date) {
		WeeklySchedule weekTodoData = scheduleService.getWeekTodo(date);
		List<ConnectInfo> connectInfoList = connectInfoConverter.convertList(weekTodoData);

		ResponseCode code = ResponseCode.OK;

		// 일정 리스트가 비었다면 일정이 비었다고 반환
		if (connectInfoList == null || connectInfoList.isEmpty())
			code = ResponseCode.JANDI_SCHEDULE_EMPTY;

		return jandiResponseGenerator.createSuccessResponse(code, connectInfoList);
	}

	@Override
	public JandiWebhookResponse scheduleDayMessage(LocalDate date) {
		WeeklySchedule weeklySchedule = scheduleService.getDayTodo(date);

		List<ConnectInfo> connectInfo = connectInfoConverter.convertList(weeklySchedule);

		// 일정 리스트가 비었다면 일정이 비었다고 반환
		if (connectInfo == null || connectInfo.isEmpty())
			return jandiResponseGenerator.createSuccessResponse(ResponseCode.JANDI_SCHEDULE_EMPTY);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfo);
	}

	@Override
	public JandiWebhookResponse chooseLotteryMessage(String imgURL) {
		LotteryWinner winner = lotteryService.drawLotteryWinner();

		ConnectInfo connectInfo = connectInfoConverter.convert(winner);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfo);
	}

	@Override
	public JandiWebhookResponse infoMessage(JandiWebhookRequest jandiWebhookRequest) {

		JandiUserInfoRequest infoRequest = JandiUserInfoRequest.from(jandiWebhookRequest);

		ConnectInfo connectInfo = connectInfoConverter.convert(infoRequest);
		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfo);
	}

	@Override
	public JandiWebhookResponse registerLotteryMessage(LotteryRequest lotteryRequest) {
		lotteryService.registerLottery(lotteryRequest);
		return jandiResponseGenerator.createSuccessResponse(ResponseCode.CREATED);
	}

	@Override
	public JandiWebhookResponse deleteLotteryMessage(LotteryRequest lotteryRequest) {
		lotteryService.deleteLottery(lotteryRequest.memberId(), lotteryRequest.lotteryName());

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.LOTTERY_DELETE_SUCCESS);
	}

	@Override
	public JandiWebhookResponse updateLotteryMessage(Long memberId, LotteryUpdateRequest request) {
		lotteryService.updateLottery(memberId, request);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.UPDATED);
	}

	@Override
	public JandiWebhookResponse lotteryListMessage(List<LotteryResponse> lotteryResponseList) {

		List<LotteryView> lotteryViewList = lotteryResponseList.stream()
				.map(LotteryView::from)
				.toList();



		List<ConnectInfo> connectInfoList = connectInfoConverter.convertList(LotteryViewList.from(lotteryViewList));

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList);
	}
}
