package com.hk_music_cop.demo.lottery.presentation;


import com.hk_music_cop.demo.ex.ApiResponse;
import com.hk_music_cop.demo.ex.ResponseCode;
import com.hk_music_cop.demo.global.security.CustomUser;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryCreateRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryTargetRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimple;
import com.hk_music_cop.demo.lottery.dto.response.LotteryUpdateLog;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/lottery")
@RequiredArgsConstructor
@RestController
public class LotteryController {
	private final LotteryService lotteryService;
	private final MemberService memberService;

	@GetMapping("/winner")
	public ResponseEntity<LotterySimple> drawLottery() {
		LotterySimple response = LotterySimple.from(lotteryService.chooseLotteryWinner());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<ApiResponse<LotteryUpdateLog>> updateLottery(@RequestBody LotteryUpdateRequest request, @AuthenticationPrincipal CustomUser customUser) {

		String username = customUser.getUsername();
		MemberResponse user = memberService.findByUserId(username);

		LotteryResponse beforeLottery = lotteryService.findByName(request.targetName());

		// 추첨 수정
		lotteryService.updateLottery(user.getMemberId(), request);

		LotteryResponse afterLottery = lotteryService.findByName(request.targetName());

		LotteryUpdateLog lotteryUpdateLog = LotteryUpdateLog.of(LotterySimple.from(beforeLottery), LotterySimple.from(afterLottery));

		ApiResponse<LotteryUpdateLog> response = ApiResponse.of(ResponseCode.UPDATED, lotteryUpdateLog);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<LotterySimple>>> getAllLotteries() {
		List<LotteryResponse> allLottery = lotteryService.getAllLottery();

		List<LotterySimple> list = allLottery
				.stream()
				.map(LotterySimple::from)
				.toList();

		ApiResponse<List<LotterySimple>> response = ApiResponse.of(ResponseCode.OK, list);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	@PostMapping("remove")
	public ResponseEntity<ApiResponse<LotterySimple>> deleteLottery(@RequestBody LotteryTargetRequest deleteRequest, @AuthenticationPrincipal UserDetails userDetails) {

		String targetName = deleteRequest.lotteryName();

		String userId = userDetails.getUsername();

		MemberResponse byUserId = memberService.findByUserId(userId);

		LotteryResponse targetLottery = lotteryService.findByName(targetName);

		lotteryService.deleteLottery(byUserId.getMemberId(), targetName);


		ApiResponse<LotterySimple> response = ApiResponse.of(ResponseCode.LOTTERY_DELETE_SUCCESS, LotterySimple.from(targetLottery));

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<LotterySimple>> createLottery(@RequestBody LotteryCreateRequest lotteryCreateRequest, @AuthenticationPrincipal UserDetails userDetails) {
		String userId = userDetails.getUsername();
		MemberResponse byUserId = memberService.findByUserId(userId);

		LotteryRequest request = LotteryRequest.of(byUserId.getMemberId(), lotteryCreateRequest);
		lotteryService.registerLottery(request);

		LotteryResponse createdLottery = lotteryService.findByName(request.lotteryName());
		ApiResponse<LotterySimple> response = ApiResponse.of(ResponseCode.LOTTERY_CREATE_SUCCESS, LotterySimple.from(createdLottery));

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

}
