package com.hk_music_cop.demo.unit.lottery.application;

import com.hk_music_cop.demo.global.common.error.exceptions.*;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.lottery.application.LotteryServiceImpl;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceImplTest {

	@Mock
	private LotteryRepository lotteryRepository;

	@InjectMocks
	private LotteryServiceImpl lotteryService;

	@Test
	@DisplayName("추첨 당첨자 선정 - 성공")
	void drawLotteryWinner_Success() {
		// given
		List<LotteryDetailResponse> lotteryList = List.of(
				new LotteryDetailResponse("추첨일", "주임", LocalDateTime.now(), 1L, 1L),
				new LotteryDetailResponse("추첨이", "이사", LocalDateTime.now(), 2L, 1L)
		);

		when(lotteryRepository.findAll()).thenReturn(lotteryList);

		// when
		LotteryWinner winner = lotteryService.drawLotteryWinner();

		// then
		assertThat(winner).isNotNull();
		assertThat(winner.lotteryName()).isIn("추첨일", "추첨이");
		assertThat(winner.position()).isIn("주임", "이사");
		verify(lotteryRepository).findAll();
	}

	@Test
	@DisplayName("추첨 당첨자 선정 - 빈 리스트")
	void drawLotteryWinner_EmptyList() {
		// given
		when(lotteryRepository.findAll()).thenReturn(Collections.emptyList());

		// when & then
		assertThatThrownBy(() -> lotteryService.drawLotteryWinner())
				.isInstanceOf(CustomLotteryNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.LOTTERY_NOT_FOUND);
	}

	@Test
	@DisplayName("추첨 등록 - 성공")
	void registerLottery_Success() {
		// given
		LotteryRequest request = new LotteryRequest(1L, "새로운추첨", "주임");
		when(lotteryRepository.existsByName("새로운추첨")).thenReturn(false);
		when(lotteryRepository.createLottery(request)).thenReturn(1L);

		// when
		Long lotteryId = lotteryService.registerLottery(request);

		// then
		assertThat(lotteryId).isEqualTo(1L);
		verify(lotteryRepository).existsByName("새로운추첨");
		verify(lotteryRepository).createLottery(request);
	}

	@Test
	@DisplayName("추첨 등록 - 중복 이름")
	void registerLottery_DuplicateName() {
		// given
		LotteryRequest request = new LotteryRequest(1L, "중복추첨", "주임");
		when(lotteryRepository.existsByName("중복추첨")).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> lotteryService.registerLottery(request))
				.isInstanceOf(CustomDuplicatedNameException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATE_NAME);
	}

	@Test
	@DisplayName("추첨 등록 - DB 에러")
	void registerLottery_DatabaseError() {
		// given
		LotteryRequest request = new LotteryRequest(1L, "새로운추첨", "주임");
		when(lotteryRepository.existsByName("새로운추첨")).thenReturn(false);
		when(lotteryRepository.createLottery(request)).thenReturn(0L);

		// when & then
		assertThatThrownBy(() -> lotteryService.registerLottery(request))
				.isInstanceOf(CustomException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.DATABASE_CREATE_ERROR);
	}

	@Test
	@DisplayName("추첨 삭제 - 성공")
	void deleteLottery_Success() {
		// given
		String lotteryName = "삭제추첨";
		Long memberId = 1L;
		LotteryDetailResponse lottery = new LotteryDetailResponse(lotteryName, "주임", LocalDateTime.now(), 1L, memberId);

		when(lotteryRepository.findByName(lotteryName)).thenReturn(Optional.of(lottery));
		when(lotteryRepository.isCreatedBy(memberId, 1L)).thenReturn(true);
		when(lotteryRepository.deleteLottery(1L)).thenReturn(1);

		// when
		lotteryService.deleteLottery(memberId, lotteryName);

		// then
		verify(lotteryRepository).findByName(lotteryName);
		verify(lotteryRepository).isCreatedBy(memberId, 1L);
		verify(lotteryRepository).deleteLottery(1L);
	}

	@Test
	@DisplayName("추첨 삭제 - 찾을 수 없음")
	void deleteLottery_NotFound() {
		// given
		String lotteryName = "없는추첨";
		Long memberId = 1L;

		when(lotteryRepository.findByName(lotteryName)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> lotteryService.deleteLottery(memberId, lotteryName))
				.isInstanceOf(CustomLotteryNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.LOTTERY_NOT_FOUND);
	}

	@Test
	@DisplayName("추첨 삭제 - 권한 없음")
	void deleteLottery_Unauthorized() {
		// given
		String lotteryName = "삭제추첨";
		Long memberId = 1L;
		LotteryDetailResponse lottery = new LotteryDetailResponse(lotteryName, "이사", LocalDateTime.now(), 1L, 2L);

		when(lotteryRepository.findByName(lotteryName)).thenReturn(Optional.of(lottery));
		when(lotteryRepository.isCreatedBy(memberId, 1L)).thenReturn(false);

		// when & then
		assertThatThrownBy(() -> lotteryService.deleteLottery(memberId, lotteryName))
				.isInstanceOf(CustomUnauthorizedException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNAUTHORIZED);
	}

	@Test
	@DisplayName("전체 추첨 조회 - 성공")
	void getAllLottery_Success() {
		// given
		List<LotteryDetailResponse> lotteryList = List.of(
				new LotteryDetailResponse("추첨일", "이사", LocalDateTime.now(), 1L, 1L),
				new LotteryDetailResponse("추첨이", "주임", LocalDateTime.now(), 2L, 1L)
		);

		when(lotteryRepository.findAll()).thenReturn(lotteryList);

		// when
		List<LotteryDetailResponse> result = lotteryService.getAllLottery();

		// then
		assertThat(result).hasSize(2);

		assertThat(result).isEqualTo(lotteryList);
		verify(lotteryRepository).findAll();
	}

	@Test
	@DisplayName("전체 추첨 조회 - 빈 리스트")
	void getAllLottery_EmptyList() {
		// given
		when(lotteryRepository.findAll()).thenReturn(Collections.emptyList());

		// when & then
		assertThatThrownBy(() -> lotteryService.getAllLottery())
				.isInstanceOf(CustomNotFoundException.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND);
	}
}
