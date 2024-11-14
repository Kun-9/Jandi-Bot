package com.hk_music_cop.demo.lottery.presentation;


import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimpleResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/lottery")
@RequiredArgsConstructor
@RestController
public class LotteryController {
	private final LotteryService lotteryService;
	private final MemberService memberService;

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

	@PostMapping("remove")
	public ResponseEntity<String> deleteLottery(@RequestBody String lotteryName, @AuthenticationPrincipal UserDetails userDetails) {

		System.out.println("lotteryName = " + lotteryName);

		String userId = userDetails.getUsername();

		MemberResponse byUserId = memberService.findByUserId(userId);

		boolean result = lotteryService.deleteLottery(byUserId.getMemberId(), lotteryName);

		if (result) return new ResponseEntity<>("삭제 성공", HttpStatus.OK);

		return new ResponseEntity<>("삭제 실패", HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<String> createLottery(@RequestBody LotteryRequest lotteryRequest, @AuthenticationPrincipal UserDetails userDetails) {
		String userId = userDetails.getUsername();
		MemberResponse byUserId = memberService.findByUserId(userId);

		LotteryRequest request = lotteryRequest.withMemberId(byUserId.getMemberId());

		lotteryService.registerLottery(request);

		return new ResponseEntity<>("등록 성공", HttpStatus.OK);
	}

}
