package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JandiMessageFormatterImpl implements JandiMessageFormatter {

	private final GoogleSheetProperties googleSheetProperties;


	@Override
	public JSONObject createResponseMessage(JandiWebhookResponse jandiWebhookResponse) {

		// JSON 응답 메시지 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("body", jandiWebhookResponse.getBody());

		if (jandiWebhookResponse.getConnectColor() != null)
			jsonObject.put("connectColor", jandiWebhookResponse.getConnectColor());

		JSONArray connectInfoJson = setConnectInfo(jandiWebhookResponse);
		jsonObject.put("connectInfo", connectInfoJson);

		return jsonObject;
	}

	@Override
	public HttpEntity<String> createResponseEntity(String webhookURL, JandiWebhookResponse JandiWebhookResponse) {

		RestTemplate restTemplate = new RestTemplate();

		// Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/vnd.tosslab.jandi-v2+json");

		// Json 응답 생성
		JSONObject jsonMessageObject = createResponseMessage(JandiWebhookResponse);


		// Http 엔티티 생성
		HttpEntity<String> entity = new HttpEntity<>(jsonMessageObject.toString(), headers);

		return restTemplate.postForEntity(webhookURL, entity, String.class);
	}

	private static JSONArray setConnectInfo(JandiWebhookResponse jandiWebhookResponse) {
		JSONArray connectInfoJson = new JSONArray();

		for (int i = 0; i < jandiWebhookResponse.getConnectInfoList().size(); i++) {
			JSONObject object = new JSONObject();
			JandiWebhookResponse.ConnectInfo connectInfo = jandiWebhookResponse.getConnectInfoList().get(i);

			if (connectInfo.getTitle() != null)
				object.put("title", connectInfo.getTitle());
			if (connectInfo.getDescription() != null)
				object.put("description", connectInfo.getDescription());
			if (connectInfo.getImageUrl() != null)
				object.put("imageUrl", connectInfo.getImageUrl());

			connectInfoJson.put(object);
		}
		return connectInfoJson;
	}

	public JandiWebhookResponse parseScheduleListToResponse(String title, String color, List<List<String>> result) {
		JandiWebhookResponse jandiWebhookResponse = new JandiWebhookResponse(title, color);

		int i = 0;
		int cnt = 0;
		for (List<String> strings : result) {
			if (strings != null) {
				StringBuilder sb = new StringBuilder();

				for (String string : strings) {
					sb.append(string).append("\n");
				}

				String content = sb.toString().trim();

				jandiWebhookResponse.addConnectInfo(new JandiWebhookResponse.ConnectInfo(googleSheetProperties.calendar().dayList().get(i) + "요일", content, null));

//				// 주간조회일 때
//				if (Boolean.FALSE.equals(isDay)) {
//					jandiWebhookRequest.addConnectInfo(new JandiSendForm.ConnectInfo(monday.plusDays(i).getMonthValue() + "일 " + dayList[i] + "요일", content, null));
//				} else {
//					jandiWebhookRequest.addConnectInfo(new JandiSendForm.ConnectInfo(now.getDayOfMonth() + "일 " + dayList[i] + "요일", content, null));
//				}
				cnt++;
			}
			i++;
		}

		if (cnt == 0) {
			jandiWebhookResponse.setConnectColor("#FE6188");
			jandiWebhookResponse.addConnectInfo(new JandiWebhookResponse.ConnectInfo("일정이 없어요", null, null));
		}

		return jandiWebhookResponse;
	}
}
