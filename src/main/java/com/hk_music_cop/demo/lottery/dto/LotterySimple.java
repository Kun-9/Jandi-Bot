package com.hk_music_cop.demo.lottery.dto;

import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import lombok.Value;
import org.springframework.util.StringUtils;

public record LotterySimple(String lotteryName, String position) {
	public static LotterySimple from(LotteryResponse lotteryResponse) {
		return new LotterySimple(lotteryResponse.getLotteryName(), lotteryResponse.getPosition());
	}

	public static LotterySimple from(LotterySimple lotterySimple) {
		return new LotterySimple(lotterySimple.lotteryName(), lotterySimple.position());
	}

	public static LotterySimple from(LotteryUpdateRequest lottery) {
		return new LotterySimple(lottery.name(), lottery.position());
	}

	public static LotterySimple of(String lotteryName, String position) {
		return new LotterySimple(lotteryName, position);
	}

	public String getLotteryStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append(lotteryName);
		if (StringUtils.hasText(position)) {
			sb.append(" ").append(position);
		}

		return sb.toString();
	}
}
