package com.hk_music_cop.demo.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.request.Lottery;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LotteryRepository {

	Long createLottery(LotteryRequest lotteryRequest);

	Optional<LotteryResponse> findByLotteryId(Long lotteryId);

	Optional<LotteryResponse> findByName(String name);

	int editLottery(@Param("targetId") Long targetId, @Param("lottery") LotterySimple lottery);

	int deleteLottery(Long lotteryId);

	List<LotteryResponse> findAll();

	boolean existsByName(String name);

	boolean isCreatedBy(@Param("memberId") Long memberId, @Param("lotteryId") Long lotteryId);
}
