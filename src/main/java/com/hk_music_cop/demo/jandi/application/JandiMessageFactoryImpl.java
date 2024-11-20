package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JandiMessageFactoryImpl implements JandiMessageFactory {

	private final JandiResponseGenerator jandiResponseGenerator;
	private final ScheduleService scheduleService;
	private final LotteryService lotteryService;
	private final JandiMessageFormatter jandiMessageFormatter;

	@Override
	public JandiWebhookResponse scheduleWeekMessage(LocalDate date) {
		WeeklySchedule weekTodoData = scheduleService.getWeekTodo(date);

		List<ConnectInfo> connectInfoList = jandiMessageFormatter.parseScheduleToResponse(weekTodoData);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList);
	}

	@Override
	public JandiWebhookResponse scheduleDayMessage(LocalDate date) {
		WeeklySchedule daySchedule = scheduleService.getDayTodo(date);

		List<ConnectInfo> connectInfoList = jandiMessageFormatter.parseScheduleToResponse(daySchedule);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList);
	}

	@Override
	public JandiWebhookResponse chooseLotteryMessage(String imgURL) {
		LotteryResponse winner = lotteryService.chooseLotteryWinner();
		ConnectInfo connectInfo = ConnectInfo.fromLotteryWinner(winner);

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfo);
	}

	@Override
	public JandiWebhookResponse infoMessage(JandiWebhookRequest jandiWebhookRequest) {
		String content = getWriterInfo(jandiWebhookRequest).toString();
		ConnectInfo connectInfo = ConnectInfo.ofSingleStringData(content);
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

		List<ConnectInfo> connectInfoList = lotteryResponseList.stream()
				.map(ConnectInfo::fromLotteryInfo)
				.toList();

		return jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList);
	}

	// 여기 있으면 안됨
	private static StringBuilder getWriterInfo(JandiWebhookRequest jandiWebhookRequest) {
		StringBuilder writerInfo = new StringBuilder();
		writerInfo
				.append("일자 : ").append(jandiWebhookRequest.getCreatedAt()).append("\n")
				.append("ID : ").append(jandiWebhookRequest.getWriter().getId()).append("\n")
				.append("이름 : ").append(jandiWebhookRequest.getWriter().getName()).append("\n")
				.append("E-Mail : ").append(jandiWebhookRequest.getWriter().getEmail()).append("\n")
				.append("TEL : ").append(jandiWebhookRequest.getWriter().getPhoneNumber()).append("\n")
				.append("키워드 : ").append(jandiWebhookRequest.getKeyword()).append("\n")
				.append("IP : ").append(jandiWebhookRequest.getIp()).append("\n")
				.append("요청 방 이름 : ").append(jandiWebhookRequest.getRoomName()).append("\n")
				.append("팀 이름 : ").append(jandiWebhookRequest.getTeamName()).append("\n")
				.append("DATA : ").append(jandiWebhookRequest.getData()).append("\n")
				.append("TEXT : ").append(jandiWebhookRequest.getText()).append("\n")
				.append("토큰 : ").append(jandiWebhookRequest.getToken()).append("\n")
				.append("플랫폼 : ").append(jandiWebhookRequest.getPlatform()).append("\n");
		return writerInfo;
	}


}
