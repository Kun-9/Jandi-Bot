package com.hk_music_cop.demo.schedule.presentation;


import com.hk_music_cop.demo.schedule.application.ScheduleService;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@RestController
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping("/day/{date}")
	public ResponseEntity<List<List<String>>> day(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		List<List<String>> dayTodoData = scheduleService.getDayTodo(date);
		return new ResponseEntity<>(dayTodoData, HttpStatus.OK);
	}

	@GetMapping("/day")
	public ResponseEntity<List<List<String>>> day() {
		List<List<String>> dayTodoData = scheduleService.getDayTodo(LocalDate.now());
		return new ResponseEntity<>(dayTodoData, HttpStatus.OK);
	}

	@GetMapping("/week/{date}")
	public ResponseEntity<List<List<String>>> week(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		List<List<String>> weekTodo = scheduleService.getWeekTodo(date);
		return new ResponseEntity<>(weekTodo, HttpStatus.OK);
	}
}
