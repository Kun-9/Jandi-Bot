package com.hk_music_cop.demo.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LotteryRepository {

	Long createLottery(LotteryRequest lotteryRequest);

	Optional<LotteryDetailResponse> findByLotteryId(Long lotteryId);

	Optional<LotteryDetailResponse> findByName(String name);

	int editLottery(@Param("targetId") Long targetId, @Param("lottery") LotteryResponse lottery);

	int deleteLottery(Long lotteryId);

	List<LotteryDetailResponse> findAll();

	boolean existsByName(String name);

	boolean isCreatedBy(@Param("memberId") Long memberId, @Param("lotteryId") Long lotteryId);
}
