package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.external.jandi.application.JandiMessageConverter;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {

	private final JandiMessageConverter jandiMessageConverter;
	private final Random rand = new Random();

	public JSONObject getRandomPerson(String title, String color) {

		String imgURL = null;

		ArrayList<String> personList = new ArrayList<>();
		personList.add("신동근"); personList.add("손은빈"); personList.add("김문진"); personList.add("정세은"); personList.add("최제원"); personList.add("이정운");

		String person = getRandom(personList);

		// 메시지 생성
		JandiWebhookRequest jandiWebhookRequest = new JandiWebhookRequest(title, color);
		jandiWebhookRequest.addConnectInfo(new JandiWebhookRequest.ConnectInfo("결과", "'" + person + "'님 당첨되었습니다.\n축하합니다~!", imgURL));


		return jandiMessageConverter.createJandiSendMessage(jandiWebhookRequest);
	}

	private String getRandom(List<String> personList) {
		return personList.get(rand.nextInt(personList.size()));
	}

	@Override
	public HttpEntity registPerson(String name) {
		return null;
	}
}
