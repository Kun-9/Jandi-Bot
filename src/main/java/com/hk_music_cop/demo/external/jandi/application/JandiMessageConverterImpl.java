package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JandiMessageConverterImpl implements JandiMessageConverter {

	private final GoogleSheetProperties googleSheetProperties;


	@Override
	public JSONObject createJandiSendMessage(JandiWebhookRequest jandiWebhookRequest) {

		// JSON 응답 메시지 생성
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("body", jandiWebhookRequest.getBody());

		if (jandiWebhookRequest.getConnectColor() != null)
			jsonObject.put("connectColor", jandiWebhookRequest.getConnectColor());

		JSONArray connectInfoJson = setConnectInfo(jandiWebhookRequest);
		jsonObject.put("connectInfo", connectInfoJson);

		return jsonObject;
	}

	@Override
	public HttpEntity<String> createJandiRequestMessageEntity(String webhookURL, JandiWebhookRequest JandiWebhookRequest) {

		RestTemplate restTemplate = new RestTemplate();

		// Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/vnd.tosslab.jandi-v2+json");

		// Json 응답 생성
		JSONObject jsonMessageObject = createJandiSendMessage(JandiWebhookRequest);


		// Http 엔티티 생성
		HttpEntity<String> entity = new HttpEntity<>(jsonMessageObject.toString(), headers);

		return restTemplate.postForEntity(webhookURL, entity, String.class);
	}

	private static JSONArray setConnectInfo(JandiWebhookRequest jandiWebhookRequest) {
		JSONArray connectInfoJson = new JSONArray();

		for (int i = 0; i < jandiWebhookRequest.getConnectInfoList().size(); i++) {
			JSONObject object = new JSONObject();
			JandiWebhookRequest.ConnectInfo connectInfo = jandiWebhookRequest.getConnectInfoList().get(i);

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

	public JandiWebhookRequest parseToRequestForm(String title, String color, List<List<String>> result) {
		JandiWebhookRequest jandiWebhookRequest = new JandiWebhookRequest(title, color);

		int i = 0;
		int cnt = 0;
		for (List<String> strings : result) {
			if (strings != null) {
				StringBuilder sb = new StringBuilder();

				for (String string : strings) {
					sb.append(string).append("\n");
				}

				String content = sb.toString().trim();

				jandiWebhookRequest.addConnectInfo(new JandiWebhookRequest.ConnectInfo(googleSheetProperties.getCalendar().getDayList().get(i) + "요일", content, null));

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
			jandiWebhookRequest.setConnectColor("#FE6188");
			jandiWebhookRequest.addConnectInfo(new JandiWebhookRequest.ConnectInfo("일정이 없어요", null, null));
		}

		return jandiWebhookRequest;
	}
}
