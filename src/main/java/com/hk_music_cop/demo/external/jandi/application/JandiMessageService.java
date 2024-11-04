package com.hk_music_cop.demo.external.jandi.application;

import org.json.JSONObject;

import java.time.LocalDate;

public interface JandiMessageService {
	JSONObject scheduleWeekMessage(LocalDate date);

	JSONObject scheduleDayMessage(LocalDate date);

	JSONObject lotteryMessage(String imgURL);

	JSONObject errorMessage();
}
