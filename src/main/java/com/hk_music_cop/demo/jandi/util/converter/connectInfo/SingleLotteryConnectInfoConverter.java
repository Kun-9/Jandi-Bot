package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;

/**
 *  단순 [이름 포지션] 을 내용으로 가지는 ConnectInfo 로 변경
 */
public interface SingleLotteryConnectInfoConverter extends ConnectInfoConverter<LotteryResponse> {
	ConnectInfo convert(LotteryResponse lotteryResponse);
}
