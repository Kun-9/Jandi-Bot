package com.hk_music_cop.demo.schedule.application;

import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import org.json.JSONObject;

import java.time.LocalDate;

public interface ScheduleService {
	JSONObject registTodo(String name, String todo);

//	JSONObject getWeekTodo(String title, String color, LocalDate date);

	WeeklySchedule getWeekTodo(LocalDate date);

//	JSONObject getTodayTodo(String title, String color);
//
	WeeklySchedule getDayTodo(LocalDate date);

	void validateHoliday(LocalDate date);

}
