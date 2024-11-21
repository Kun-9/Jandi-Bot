package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryView;
import com.hk_music_cop.demo.lottery.dto.response.LotteryViewList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LotteryViewListConverterImpl implements LotteryViewListConverter {

	private final ConnectInfoConverter<LotteryView> converter;

	@Override
	public boolean supports(Class<?> sourceType) {
		return LotteryViewList.class.isAssignableFrom(sourceType);
	}

	@Override
	public List<ConnectInfo> convertToList(LotteryViewList lotteryViewList) {

		return lotteryViewList.lotteryViews().stream()
				.map(converter::convert)
				.toList();
	}
}
