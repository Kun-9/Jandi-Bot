package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;

public interface LotteryWinnerConverter extends ConnectInfoConverter<LotteryWinner> {
	ConnectInfo convert(LotteryWinner lotteryWinner);
}
