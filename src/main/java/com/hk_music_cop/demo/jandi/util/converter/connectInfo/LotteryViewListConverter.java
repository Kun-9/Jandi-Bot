package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.lottery.dto.response.LotteryViewList;

import java.util.List;

public interface LotteryViewListConverter extends ConnectInfoListConverter<LotteryViewList> {
	List<ConnectInfo> convertToList(LotteryViewList lotteryViewList);
}
