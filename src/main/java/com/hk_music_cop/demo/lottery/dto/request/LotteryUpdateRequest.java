package com.hk_music_cop.demo.lottery.dto.request;

import com.hk_music_cop.demo.lottery.common.annotation.EnumValidator;
import com.hk_music_cop.demo.lottery.common.enums.Position;
import com.hk_music_cop.demo.lottery.message.LotteryValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LotteryUpdateRequest(
		@NotBlank(message = LotteryValidationMessage.CommonMessage.NAME_BLANK)
		@Size(max = 5, message = LotteryValidationMessage.CommonMessage.NAME_SIZE)
		@Pattern(regexp = "^[가-힣a-zA-Z]*$", message = LotteryValidationMessage.CommonMessage.NAME_FORMAT)
		String targetName,

		@NotBlank(message = LotteryValidationMessage.CommonMessage.NAME_BLANK)
		@Size(max = 5, message = LotteryValidationMessage.CommonMessage.NAME_SIZE)
		@Pattern(regexp = "^[가-힣a-zA-Z]*$", message = LotteryValidationMessage.CommonMessage.NAME_FORMAT)
		String name,

		@EnumValidator(enumClass = Position.class, message = LotteryValidationMessage.CommonMessage.POSITION_FORMAT)
		String position) {
	public static LotteryUpdateRequest of(String targetName, String name, String position) {
		return new LotteryUpdateRequest(targetName, name, position);
	}
	public static LotteryUpdateRequest of(String targetName, LotteryRequest lotteryRequest) {
		return new LotteryUpdateRequest(targetName, lotteryRequest.lotteryName(), lotteryRequest.position());
	}
}
