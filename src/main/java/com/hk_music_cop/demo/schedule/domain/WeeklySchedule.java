package com.hk_music_cop.demo.schedule.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


@Value
public class WeeklySchedule {

	List<DailySchedule> dailySchedules;

	public static WeeklySchedule from(List<List<String>> rawSchedules) {
		List<DailySchedule> dailySchedules = new ArrayList<>();

		for (int i = 0; i < rawSchedules.size(); i++) {
			DayOfWeek dayOfWeek = DayOfWeek.of(i + 1);
			List<String> todos = rawSchedules.get(i);

			if (todos == null) continue;
			if (rawSchedules.size() == 1) dayOfWeek = null;

			dailySchedules.add(new DailySchedule(dayOfWeek, todos));
		}
		return new WeeklySchedule(dailySchedules);
	}

	public boolean isEmpty() {
		return dailySchedules.isEmpty();
	}

	public boolean isDaySchedule() {
		return dailySchedules.size() == 1;
	}
}
