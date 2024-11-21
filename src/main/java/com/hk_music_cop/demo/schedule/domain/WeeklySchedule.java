package com.hk_music_cop.demo.schedule.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class WeeklySchedule {

	List<DailySchedule> dailySchedules;
	LocalDate date;

	public static WeeklySchedule of(List<List<String>> rawSchedules, LocalDate targetDate) {
		List<DailySchedule> dailySchedules = new ArrayList<>();

		for (int i = 0; i < rawSchedules.size(); i++) {
			DayOfWeek dayOfWeek = DayOfWeek.of(i + 1);
			List<String> todos = rawSchedules.get(i);

			// 해당 요일의 todo 가 없다면 continue
			if (todos == null) continue;

			// Daily 캘린더일때는 입력한 날짜를 기준으로 요일 계산
			if (rawSchedules.size() == 1) dayOfWeek = targetDate.getDayOfWeek();

			dailySchedules.add(new DailySchedule(dayOfWeek, todos));
		}
		return new WeeklySchedule(dailySchedules, targetDate);
	}

	public boolean isEmpty() {
		return dailySchedules.isEmpty();
	}

	public boolean isDailySchedule() {
		return dailySchedules.size() == 1;
	}
}
