package com.hk_music_cop.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
public class HomeController {

	@GetMapping
	public String home() {
		System.out.println("ScheduleController.home");

		return "hello";
	}
}
