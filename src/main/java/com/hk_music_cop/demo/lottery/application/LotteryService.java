package com.hk_music_cop.demo.lottery.application;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

public interface LotteryService {
	JSONObject getRandomPerson(String title, String color);

	HttpEntity registPerson(String name);
}
