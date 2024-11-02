package com.hk_music_cop.demo.schedule.api.jandi_webhook;

import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookRequest;
import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JandiWebhookController {

	private final ScheduleService scheduleService;
	private final JandiMessageConverter jandiMessageConverter;
	private final LotteryService lotteryService;

	@GetMapping("/jandi")
	public String jandi() {
		return "jandi-webhook-test/jandi-test";
	}

	@PostMapping("/jandi/message")
	public String sendMessage(String webhookURL, String content, String color, String title, String description) {

//		String content = "test content";
//		String color = "#FAC11B";
//		String title = "test title";
//		String description = "test description";

		JandiWebhookRequest jandiWebhookRequest = new JandiWebhookRequest(content, color);
		jandiWebhookRequest.addConnectInfo(new JandiWebhookRequest.ConnectInfo(title, description, null));

		// web hook url 설정
		webhookURL = "https://wh.jandi.com/connect-api/webhook/23002156/ad2476253597a22daaecdb0961fd25bd";

		jandiMessageConverter.createJandiRequestMessageEntity(webhookURL, jandiWebhookRequest);

//		ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

		return "redirect:/jandi";
	}

	@ResponseBody
	@PostMapping("/jandi")
	public String sendJandiMessage(@RequestBody JandiWebhookResponse jandiWebhookResponse) {

		StringBuilder writerInfo = getWriterInfo(jandiWebhookResponse);

		log.info(writerInfo.toString());

		String keyword = jandiWebhookResponse.getData();

		JSONObject jandiSendMessage;

		switch (keyword) {
			case "이번주 일정":
				return weekSchedule();
			case "오늘 일정":
				return todaySchedule();
			case "랜덤 추첨":
				return lotteryResult();
			case "내 정보":
				return myInfo(jandiWebhookResponse);
			default:
				throw new IllegalArgumentException("알 수 없는 명령");

		}

//		return jandiTransfer.createJandiSendMessage(new JandiWebhookRequest("잘못된 요청입니다.\n요청값을 확인해주세요.", "#FF6188"));
	}


	private String weekSchedule() {
		String title = "이번주 일정";
		String color = "#009DDC";
		return scheduleService.getWeekTodo(title, color).toString();
	}


	private String todaySchedule() {
		String title = "오늘 일정";
		String color = "#009DDC";
		return scheduleService.getTodayTodo(title, color).toString();
	}

	private String lotteryResult() {
		String title = "랜덤 추첨";
		String color = "#78DCE7";
		return lotteryService.getRandomPerson(title, color).toString();
	}

	private String myInfo(JandiWebhookResponse jandiWebhookResponse) {
		String title = "내 정보";
		String color = "#78DCE7";

		return null;
	}

	private static StringBuilder getWriterInfo(JandiWebhookResponse jandiWebhookResponse) {
		StringBuilder writerInfo = new StringBuilder();
		writerInfo
				.append("일자 : ").append(jandiWebhookResponse.getCreatedAt()).append("\n")
				.append("ID : ").append(jandiWebhookResponse.getWriter().getId()).append("\n")
				.append("이름 : ").append(jandiWebhookResponse.getWriter().getName()).append("\n")
				.append("E-Mail : ").append(jandiWebhookResponse.getWriter().getEmail()).append("\n")
				.append("TEL : ").append(jandiWebhookResponse.getWriter().getPhoneNumber()).append("\n")
				.append("키워드 : ").append(jandiWebhookResponse.getKeyword()).append("\n")
				.append("IP : ").append(jandiWebhookResponse.getIp()).append("\n")
				.append("요청 방 이름 : ").append(jandiWebhookResponse.getRoomName()).append("\n")
				.append("팀 이름 : ").append(jandiWebhookResponse.getTeamName()).append("\n")
				.append("DATA : ").append(jandiWebhookResponse.getData()).append("\n")
				.append("TEXT : ").append(jandiWebhookResponse.getText()).append("\n")
				.append("토큰 : ").append(jandiWebhookResponse.getToken()).append("\n")
				.append("플랫폼 : ").append(jandiWebhookResponse.getPlatform()).append("\n");
		return writerInfo;
	}
}
