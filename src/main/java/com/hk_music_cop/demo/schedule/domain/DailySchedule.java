package com.hk_music_cop.demo.schedule.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Value
public class DailySchedule {
	DayOfWeek day;
	List<Todo> todos;

	public DailySchedule(DayOfWeek day, List<String> rawTodos) {
		this.day = day;
		this.todos = rawTodos == null ? Collections.emptyList()
				: rawTodos.stream()
				.map(Todo::new)
				.toList();
	}

	public boolean hasTodos() {
		return !todos.isEmpty();
	}

	public String getDayName() {
		return day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
	}
}
