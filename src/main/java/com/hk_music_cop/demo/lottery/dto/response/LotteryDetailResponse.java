package com.hk_music_cop.demo.lottery.dto.response;

import com.hk_music_cop.demo.lottery.common.enums.Position;
import lombok.*;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @AllArgsConstructor
public class LotteryDetailResponse {
	private String lotteryName;
	private String position;
	private LocalDateTime createdDate;
	private Long lotteryId;
	private Long createdMemberId;
}
