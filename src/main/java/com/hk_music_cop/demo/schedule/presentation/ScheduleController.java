package com.hk_music_cop.demo.schedule.presentation;


import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping("/day/{date}")
	public ResponseEntity<WeeklySchedule> day(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		WeeklySchedule dayTodo = scheduleService.getDayTodo(date);

		return new ResponseEntity<>(dayTodo, HttpStatus.OK);
	}

	@GetMapping("/day")
	public ResponseEntity<WeeklySchedule> day() {
		WeeklySchedule dayTodo = scheduleService.getDayTodo(LocalDate.now());
		return new ResponseEntity<>(dayTodo, HttpStatus.OK);
	}

	@GetMapping("/week/{date}")
	public ResponseEntity<WeeklySchedule> week(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		WeeklySchedule weekTodo = scheduleService.getWeekTodo(date);
		return new ResponseEntity<>(weekTodo, HttpStatus.OK);
	}

	@GetMapping("/week")
	public ResponseEntity<WeeklySchedule> week() {
		WeeklySchedule weekTodo = scheduleService.getWeekTodo(LocalDate.now());
		return new ResponseEntity<>(weekTodo, HttpStatus.OK);
	}
}
