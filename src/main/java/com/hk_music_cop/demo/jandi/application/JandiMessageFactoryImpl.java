package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.config.JandiProperties;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JandiMessageFactoryImpl implements JandiMessageFactory {

	private final ScheduleService scheduleService;
	private final LotteryService lotteryService;
	private final JandiMessageFormatter jandiMessageFormatter;
	private final JandiProperties jandiProperties;

	@Override
	public JSONObject scheduleWeekMessage(LocalDate date) {
		String title = jandiProperties.title().weekScheduleTitle();
		String color = jandiProperties.color().successColor();

		return createJandiMessage(createScheduleWeekResponse(title, color, date));
	}

	@Override
	public JSONObject scheduleDayMessage(LocalDate date) {
		String title = jandiProperties.title().dayScheduleTitle();
		String color = jandiProperties.color().successColor();

		return createJandiMessage(createScheduleDayResponse(title, color, date));
	}

	@Override
	public JSONObject chooseLotteryMessage(String imgURL) {
		String title = jandiProperties.title().lotteryTitle();
		String color = jandiProperties.color().successColor();

		LotteryResponse winner = lotteryService.chooseLotteryWinner();

		return createJandiMessage(createLotteryResponse(title, color, winner, imgURL));
	}

	@Override
	public JSONObject errorMessage(String message) {
		JandiWebhookResponse jandiWebhookResponse = new JandiWebhookResponse(
				"ERROR MESSAGE",
				jandiProperties.color().failColor(),
				new ConnectInfo(message, null, null)
		);


		return createJandiMessage(jandiWebhookResponse);
	}

	@Override
	public JSONObject infoMessage(JandiWebhookRequest jandiWebhookRequest) {
		String title = jandiProperties.title().infoTitle();
		String color = jandiProperties.color().successColor();

		String content = getWriterInfo(jandiWebhookRequest).toString();

		JandiWebhookResponse myInfoResponse = createMyInfoResponse(title, color, content);

		return createJandiMessage(myInfoResponse);
	}

	@Override
	public JSONObject registerLotteryMessage(LotteryRequest lotteryRequest) {
		boolean result = lotteryService.registerLottery(lotteryRequest);
		JandiWebhookResponse response = createResultResponse(result);
		return createJandiMessage(response);
	}

	@Override
	public JSONObject deleteLotteryMessage(LotteryRequest lotteryRequest) {
		boolean result = lotteryService.deleteLottery(lotteryRequest.memberId(), lotteryRequest.lotteryName());

		JandiWebhookResponse response = createResultResponse(result);

		return createJandiMessage(response);
	}

	@Override
	public JSONObject updateLotteryMessage(Long targetLotteryId, LotteryRequest lotteryRequest) {
		boolean result = lotteryService.updateLottery(lotteryRequest.memberId(), targetLotteryId, lotteryRequest);

		JandiWebhookResponse response = createResultResponse(result);

		return createJandiMessage(response);
	}

	private JandiWebhookResponse createResultResponse(boolean result) {
		String title;
		String color;
		String mainTitle;

		if (result) {
			mainTitle = "SUCCESS";
			title = jandiProperties.title().successTitle();
			color = jandiProperties.color().successColor();
		} else {
			mainTitle = "FAIL";
			title = jandiProperties.title().failTitle();
			color = jandiProperties.color().failColor();
		}

		return new JandiWebhookResponse(
				mainTitle,
				color,
				new ConnectInfo(title, null, null)
		);

	}

	@Override
	public JSONObject lotteryListMessage(List<LotteryResponse> lotteryResponseList) {


		List<ConnectInfo> connectInfoList = lotteryResponseList.stream()
				.map(lotteryResponse ->
						new ConnectInfo(
								lotteryResponse.getLotteryName() + " " + lotteryResponse.getPosition(),
								null,
								null
						)
				).toList();

		JandiWebhookResponse response = new JandiWebhookResponse(
				"리스트 조회",
				jandiProperties.color().successColor(),
				connectInfoList
		);


		return createJandiMessage(response);
	}

	private JandiWebhookResponse createLotteryResponse(String title, String color, LotteryResponse person, String imgURL) {
		return new JandiWebhookResponse(
				title,
				color,
				new ConnectInfo("결과", "'" + person.getLotteryName() + " " + person.getPosition() + "'님 당첨되었습니다.\n축하합니다~!", imgURL)
		);
	}

	private JandiWebhookResponse createScheduleWeekResponse(String title, String color, LocalDate date) {
		WeeklySchedule weekTodoData = scheduleService.getWeekTodo(date);
		return jandiMessageFormatter.parseScheduleListToResponse(title, color, weekTodoData);
	}

	private JandiWebhookResponse createScheduleDayResponse(String title, String color, LocalDate date) {
		WeeklySchedule dayTodo = scheduleService.getDayTodo(date);
		return jandiMessageFormatter.parseScheduleListToResponse(title, color, dayTodo);
	}

	private JSONObject createJandiMessage(JandiWebhookResponse jandiWebhookResponse) {
		return jandiMessageFormatter.createResponseMessage(jandiWebhookResponse);
	}

	private JandiWebhookResponse createMyInfoResponse(String title, String color, String content) {
		return new JandiWebhookResponse(
				title,
				color,
				new ConnectInfo("정보", content, null)
		);
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
