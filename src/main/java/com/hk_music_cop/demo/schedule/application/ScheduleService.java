package com.hk_music_cop.demo.schedule.application;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
	JSONObject registTodo(String name, String todo);

//	JSONObject getWeekTodo(String title, String color, LocalDate date);

	List<List<String>> getWeekTodo(LocalDate date);

//	JSONObject getTodayTodo(String title, String color);
//
	List<List<String>> getDayTodo(LocalDate date);

	void validateHoliday(LocalDate date);

}
