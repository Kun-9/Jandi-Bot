package com.hk_music_cop.demo.lottery.presentation;


import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimpleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/lottery")
@RequiredArgsConstructor
@RestController
public class LotteryController {
	private final LotteryService lotteryService;

	@GetMapping("/winner")
	public ResponseEntity<LotterySimpleResponse> drawLottery() {
		LotterySimpleResponse response = LotterySimpleResponse.from(lotteryService.chooseLotteryWinner());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<LotterySimpleResponse>> getAllLotteries() {
		List<LotteryResponse> allLottery = lotteryService.getAllLottery();

		List<LotterySimpleResponse> list = allLottery
				.stream()
				.map(LotterySimpleResponse::from)
				.toList();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
