package com.hk_music_cop.demo.schedule.application;

import org.json.JSONObject;

import java.util.List;

public interface ScheduleService {
	JSONObject registTodo(String name, String todo);

	JSONObject getWeekTodo(String title, String color);

	List<List<String>> getWeekTodoData(String title, String color);

	JSONObject getTodayTodo(String title, String color);

	List<List<String>> getTodayTodoData(String title, String color);
}
