package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryView;
import org.springframework.stereotype.Component;

@Component
public class LotteryViewConverterImpl implements LotteryViewConverter {

	@Override
	public boolean supports(Class<?> sourceType) {
		return LotteryView.class.isAssignableFrom(sourceType);
	}

	@Override
	public ConnectInfo convert(LotteryView lotteryView) {

		StringBuilder data = new StringBuilder();
		data.append(lotteryView.lotteryName());

		if (lotteryView.position() != null) {
			data.append(" ").append(lotteryView.position());
		}

		return ConnectInfo.fromDescription(data.toString());
	}
}
