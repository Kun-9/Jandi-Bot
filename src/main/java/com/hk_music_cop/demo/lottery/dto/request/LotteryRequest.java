package com.hk_music_cop.demo.lottery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class LotteryRequest {
	private Long memberId;
	private String name;
	private String position;
}
