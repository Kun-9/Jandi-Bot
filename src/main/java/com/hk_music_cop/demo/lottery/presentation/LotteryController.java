package com.hk_music_cop.demo.lottery.presentation;


import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.security.common.CustomUser;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryCreateRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryDeleteRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryUpdateLog;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
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
	public ResponseEntity<ApiResponse<LotteryWinner>> drawLottery() {
		LotteryWinner winner = lotteryService.drawLotteryWinner();

		ApiResponse<LotteryWinner> response = ApiResponse.of(ResponseCode.OK, winner);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@PostMapping("/update")
	public ResponseEntity<ApiResponse<LotteryUpdateLog>> updateLottery(@RequestBody LotteryUpdateRequest request, @AuthenticationPrincipal CustomUser customUser) {

		String username = customUser.getUsername();
		MemberResponse user = memberService.findByUserId(username);

		// 추첨 수정
		LotteryUpdateLog lotteryUpdateLog = lotteryService.updateLottery(user.getMemberId(), request);

		ApiResponse<LotteryUpdateLog> response = ApiResponse.of(ResponseCode.UPDATED, lotteryUpdateLog);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<LotteryResponse>>> getAllLotteries() {
		List<LotteryDetailResponse> allLottery = lotteryService.getAllLottery();

		List<LotteryResponse> list = allLottery
				.stream()
				.map(LotteryResponse::from)
				.toList();

		ApiResponse<List<LotteryResponse>> response = ApiResponse.of(ResponseCode.OK, list);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@PostMapping("remove")
	public ResponseEntity<ApiResponse<LotteryResponse>> deleteLottery(@RequestBody LotteryDeleteRequest deleteRequest, @AuthenticationPrincipal UserDetails userDetails) {

		String targetName = deleteRequest.lotteryName();

		String userId = userDetails.getUsername();

		MemberResponse byUserId = memberService.findByUserId(userId);

		LotteryDetailResponse targetLottery = lotteryService.findByName(targetName);

		lotteryService.deleteLottery(byUserId.getMemberId(), targetName);


		ApiResponse<LotteryResponse> response = ApiResponse.of(ResponseCode.LOTTERY_DELETE_SUCCESS, LotteryResponse.from(targetLottery));

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<LotteryResponse>> createLottery(@RequestBody LotteryCreateRequest lotteryCreateRequest, @AuthenticationPrincipal UserDetails userDetails) {
		String userId = userDetails.getUsername();
		MemberResponse byUserId = memberService.findByUserId(userId);

		LotteryRequest request = LotteryRequest.of(byUserId.getMemberId(), lotteryCreateRequest);
		lotteryService.registerLottery(request);

		LotteryDetailResponse createdLottery = lotteryService.findByName(request.lotteryName());
		ApiResponse<LotteryResponse> response = ApiResponse.of(ResponseCode.LOTTERY_CREATE_SUCCESS, LotteryResponse.from(createdLottery));

		return ResponseEntity
				.status(response.status())
				.body(response);
	}
}
