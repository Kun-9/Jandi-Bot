package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryView;

public interface LotteryViewConverter extends ConnectInfoConverter<LotteryView> {
	ConnectInfo convert(LotteryView lotteryView);
}
