package com.hk_music_cop.demo.lottery.presentation;

import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryTargetRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.LotterySimple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/manager/lottery")
@RequiredArgsConstructor
@RestController
public class LotteryManagerController {

	private final LotteryService lotteryService;

	@GetMapping("/remove")
	public ResponseEntity<ApiResponse<LotterySimple>> drawLottery(@RequestBody LotteryTargetRequest request) {

		String targetName = request.lotteryName();
		LotteryResponse target = lotteryService.findByName(targetName);

		lotteryService.deleteLotteryByManager(targetName);

		ApiResponse<LotterySimple> response = ApiResponse.of(ResponseCode.LOTTERY_DELETE_SUCCESS, LotterySimple.from(target));

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}
}