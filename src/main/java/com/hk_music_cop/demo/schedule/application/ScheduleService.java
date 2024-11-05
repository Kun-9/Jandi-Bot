package com.hk_music_cop.demo.schedule.application;

import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
	JSONObject registTodo(String name, String todo);

//	JSONObject getWeekTodo(String title, String color, LocalDate date);

	List<List<String>> getWeekTodoData(String title, String color, LocalDate date);

//	JSONObject getTodayTodo(String title, String color);
//
	List<List<String>> getDayTodoData(String title, String color, LocalDate date);

	void validateHoliday(LocalDate date);

}
