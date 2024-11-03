package com.hk_music_cop.demo.lottery.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @ToString
public class LotteryResponse {
	private String name;
	private String position;
	private LocalDateTime createdDate;
	private Long lotteryId;
	private Long createdMemberId;

	@Builder
	public LotteryResponse(String name, String position, LocalDateTime createdDate,
	                       Long lotteryId, Long createdMemberId) {
		this.name = name;
		this.position = position;
		this.createdDate = createdDate;
		this.lotteryId = lotteryId;
		this.createdMemberId = createdMemberId;
	}
}
