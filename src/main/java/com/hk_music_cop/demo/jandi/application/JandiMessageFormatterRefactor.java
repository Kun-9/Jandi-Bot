package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomNotFoundException;
import com.hk_music_cop.demo.googleCloud.googleSheet.GoogleSheetProperties;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.Todo;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse.ConnectInfo;

@Component
@RequiredArgsConstructor
public class JandiMessageFormatterRefactor implements JandiMessageFormatter {

	private final GoogleSheetProperties googleSheetProperties;

	@Override
	public JSONObject jandiResponseToJsonObject(JandiWebhookResponse jandiWebhookResponse) {

		// JSON 응답 메시지 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("body", jandiWebhookResponse.body());

		if (jandiWebhookResponse.connectColor() != null)
			jsonObject.put("connectColor", jandiWebhookResponse.connectColor());

		JSONArray connectInfoJson = setConnectInfo(jandiWebhookResponse);
		jsonObject.put("connectInfo", connectInfoJson);

		return jsonObject;
	}

	public List<ConnectInfo> parseScheduleToResponse(WeeklySchedule weeklySchedule) {
		// 일정이 있는지 검증
		validateExistSchedule(weeklySchedule);

		// WeeklySchedule을 ConnectInfo List 로 변환
		return ConnectInfo.from(weeklySchedule);
	}

	private static void validateExistSchedule(WeeklySchedule weeklySchedule) {
		if (weeklySchedule.isEmpty()) throw new CustomNotFoundException("일정이 없어요");
	}

	@Override
	public HttpEntity<String> sendWebhookRequest(String webhookURL, JandiWebhookResponse JandiWebhookResponse) {
		RestTemplate restTemplate = new RestTemplate();

		// Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/vnd.tosslab.jandi-v2+json");

		// Json 응답 생성
		JSONObject jsonMessageObject = jandiResponseToJsonObject(JandiWebhookResponse);


		// Http 엔티티 생성
		HttpEntity<String> entity = new HttpEntity<>(jsonMessageObject.toString(), headers);

		return restTemplate.postForEntity(webhookURL, entity, String.class);
	}

	private static JSONArray setConnectInfo(JandiWebhookResponse jandiWebhookResponse) {
		JSONArray connectInfoJson = new JSONArray();

		for (int i = 0; i < jandiWebhookResponse.connectInfo().size(); i++) {
			JSONObject object = new JSONObject();
			ConnectInfo connectInfo = jandiWebhookResponse.connectInfo().get(i);

			if (connectInfo.title() != null)
				object.put("title", connectInfo.title());
			if (connectInfo.description() != null)
				object.put("description", connectInfo.description());
			if (connectInfo.imageUrl() != null)
				object.put("imageUrl", connectInfo.imageUrl());

			connectInfoJson.put(object);
		}
		return connectInfoJson;
	}

	private String getDayName(int dayOfWeekValue) {
		return googleSheetProperties.calendar().dayList().get(dayOfWeekValue) + "요일";
	}
}
