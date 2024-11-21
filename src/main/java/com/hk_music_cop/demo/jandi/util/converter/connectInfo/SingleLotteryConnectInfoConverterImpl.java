package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.LotterySimple;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.springframework.stereotype.Component;

@Component
public class SingleLotteryConnectInfoConverterImpl implements SingleLotteryConnectInfoConverter {

	@Override
	public boolean supports(Class<?> sourceType) {
		return LotteryResponse.class.isAssignableFrom(sourceType);
	}

	@Override
	public ConnectInfo convert(LotteryResponse lotteryResponse) {
		return ConnectInfo.withOutImg(LotterySimple.from(lotteryResponse).getLotteryStrings(), null);
	}
}
