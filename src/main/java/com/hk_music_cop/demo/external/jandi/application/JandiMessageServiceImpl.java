package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.JandiProperties;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JandiMessageServiceImpl implements JandiMessageService {

	private final ScheduleService scheduleService;
	private final LotteryService lotteryService;
	private final JandiMessageConverter jandiMessageConverter;
	private final JandiProperties jandiProperties;

	@Override
	public JSONObject scheduleWeekMessage(LocalDate date) {
		String title = "주 일정";
		String color = jandiProperties.getColor().getSuccessColor();

		return createJandiMessage(createScheduleWeekResponse(title, color, date));
	}

	@Override
	public JSONObject scheduleDayMessage(LocalDate date) {
		String title = "일 일정";
		String color = jandiProperties.getColor().getSuccessColor();

		return createJandiMessage(createScheduleDayResponse(title,color,date));
	}

	@Override
	public JSONObject lotteryMessage(String imgURL) {
		String title = "추첨 결과";
		String color = jandiProperties.getColor().getSuccessColor();

		LotteryResponse winner = lotteryService.chooseLotteryWinner(title, color, imgURL);

		JandiWebhookResponse lotteryMessage = createLotteryResponse(title, color, winner, imgURL);

		return createJandiMessage(lotteryMessage);
	}

	private JandiWebhookResponse createLotteryResponse(String title, String color, LotteryResponse person, String imgURL) {
		JandiWebhookResponse jandiWebhookResponse = new JandiWebhookResponse(title, color);
		jandiWebhookResponse.addConnectInfo(new JandiWebhookResponse.ConnectInfo("결과", "'" + person.getName() + " " + person.getPosition() +  "'님 당첨되었습니다.\n축하합니다~!", imgURL));
		return jandiWebhookResponse;
	}

	@Override
	public JSONObject errorMessage() {
		JandiWebhookResponse jandiWebhookResponse = new JandiWebhookResponse(
				"잘못된 요청입니다.\n요청값을 확인해주세요.",
				jandiProperties.getColor().getWarningColor()
		);

		return createJandiMessage(jandiWebhookResponse);
	}

	private JandiWebhookResponse createScheduleWeekResponse(String title, String color, LocalDate date) {
		List<List<String>> weekTodoData = scheduleService.getWeekTodoData(title, color, date);
		return jandiMessageConverter.parseScheduleListToRequestForm(title, color, weekTodoData);
	}

	private JandiWebhookResponse createScheduleDayResponse(String title, String color, LocalDate date) {
		List<List<String>> dayTodoData = scheduleService.getDayTodoData(title, color, date);
		return jandiMessageConverter.parseScheduleListToRequestForm(title, color, dayTodoData);
	}

	private JSONObject createJandiMessage(JandiWebhookResponse jandiWebhookResponse) {
		return jandiMessageConverter.createJandiSendMessage(jandiWebhookResponse);
	}

}
