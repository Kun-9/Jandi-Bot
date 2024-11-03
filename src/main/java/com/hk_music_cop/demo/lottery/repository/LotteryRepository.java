package com.hk_music_cop.demo.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LotteryRepository {

	Long createLottery(LotteryRequest lotteryRequest);

	LotteryResponse findByLotteryId(Long lotteryId);

	LotteryResponse findByName(String name);

	int editLottery(@Param("lotteryId") Long lotteryId, @Param("lotteryRequest") LotteryRequest lotteryRequest);

	int deleteLottery(Long lotteryId);

	List<LotteryResponse> findAll();
}
