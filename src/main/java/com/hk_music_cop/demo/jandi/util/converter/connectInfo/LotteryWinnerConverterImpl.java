package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
import org.springframework.stereotype.Component;

@Component
public class LotteryWinnerConverterImpl implements LotteryWinnerConverter {

	@Override
	public ConnectInfo convert(LotteryWinner lotteryWinner) {
		String data = String.format("%s님 당첨되었습니다.\n축하합니다~!", lotteryWinner.lotteryName());
		return ConnectInfo.fromDescription(data);
	}

	@Override
	public boolean supports(Class<?> sourceType) {
		return LotteryWinner.class.isAssignableFrom(sourceType);
	}
}
