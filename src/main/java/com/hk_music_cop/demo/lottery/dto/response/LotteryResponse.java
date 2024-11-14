package com.hk_music_cop.demo.lottery.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @ToString @AllArgsConstructor
public class LotteryResponse {
	private String lotteryName;
	private String position;
	private LocalDateTime createdDate;
	private Long lotteryId;
	private Long createdMemberId;

}
