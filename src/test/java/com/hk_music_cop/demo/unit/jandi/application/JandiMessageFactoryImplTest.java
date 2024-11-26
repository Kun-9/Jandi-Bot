package com.hk_music_cop.demo.unit.jandi.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.application.JandiMessageFactoryImpl;
import com.hk_music_cop.demo.jandi.application.JandiResponseGenerator;
import com.hk_music_cop.demo.jandi.dto.request.JandiUserInfoRequest;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookRequest;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.util.converter.connectInfo.ConnectInfoConverterComposite;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryView;
import com.hk_music_cop.demo.lottery.dto.response.LotteryViewList;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JandiMessageFactoryImplTest {

	@Mock
	private JandiResponseGenerator jandiResponseGenerator;
	@Mock
	private ScheduleService scheduleService;
	@Mock
	private LotteryService lotteryService;
	@Mock
	private ConnectInfoConverterComposite connectInfoConverter;

	@InjectMocks
	private JandiMessageFactoryImpl jandiMessageFactory;

	@Test
	@DisplayName("주간 일정 조회 - 일정이 있는 경우")
	void scheduleWeekMessage_WithSchedule() {
		// given
		LocalDate targetDate = LocalDate.now();
		List<DailySchedule> dailySchedules = List.of(
				new DailySchedule(DayOfWeek.MONDAY, List.of("task1", "task2")),
				new DailySchedule(DayOfWeek.TUESDAY, List.of("task3"))
		);
		WeeklySchedule weeklySchedule = new WeeklySchedule(dailySchedules, targetDate);

		List<ConnectInfo> connectInfoList = List.of(
				new ConnectInfo("월요일", "task1, task2", null),
				new ConnectInfo("화요일", "task3", null)
		);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.OK.getMessage(),
				"successColor",
				connectInfoList
		);

		when(scheduleService.getWeekTodo(targetDate)).thenReturn(weeklySchedule);
		when(connectInfoConverter.convertList(weeklySchedule)).thenReturn(connectInfoList);
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.scheduleWeekMessage(targetDate);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(scheduleService).getWeekTodo(targetDate);
		verify(connectInfoConverter).convertList(weeklySchedule);
	}

	@Test
	@DisplayName("주간 일정 조회 - 일정이 없는 경우")
	void scheduleWeekMessage_EmptySchedule() {
		// given
		LocalDate targetDate = LocalDate.now();
		WeeklySchedule emptySchedule = new WeeklySchedule(Collections.emptyList(), targetDate);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.JANDI_SCHEDULE_EMPTY.getMessage(),
				"successColor",
				null
		);

		when(scheduleService.getWeekTodo(targetDate)).thenReturn(emptySchedule);
		when(connectInfoConverter.convertList(emptySchedule)).thenReturn(Collections.emptyList());
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.JANDI_SCHEDULE_EMPTY, Collections.emptyList()))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.scheduleWeekMessage(targetDate);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiResponseGenerator).createSuccessResponse(
				ResponseCode.JANDI_SCHEDULE_EMPTY,
				Collections.emptyList()
		);
	}

	@Test
	@DisplayName("일간 일정 조회 - 일정이 있는 경우")
	void scheduleDayMessage_WithSchedule() {
		// given
		LocalDate targetDate = LocalDate.now();
		List<DailySchedule> dailySchedules = List.of(
				new DailySchedule(targetDate.getDayOfWeek(), List.of("task1", "task2"))
		);
		WeeklySchedule dailySchedule = new WeeklySchedule(dailySchedules, targetDate);

		List<ConnectInfo> connectInfoList = List.of(
				new ConnectInfo("오늘", "task1, task2", null)
		);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.OK.getMessage(),
				"successColor",
				connectInfoList
		);

		when(scheduleService.getDayTodo(targetDate)).thenReturn(dailySchedule);
		when(connectInfoConverter.convertList(dailySchedule)).thenReturn(connectInfoList);
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.scheduleDayMessage(targetDate);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(scheduleService).getDayTodo(targetDate);
		verify(connectInfoConverter).convertList(dailySchedule);
	}

	@Test
	@DisplayName("추첨 실행")
	void chooseLotteryMessage() {
		// given
		LotteryWinner winner = new LotteryWinner("당첨자", "position1");
		ConnectInfo winnerInfo = new ConnectInfo("추첨 결과", "당첨자 - position1", null);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.OK.getMessage(),
				"successColor",
				List.of(winnerInfo)
		);

		when(lotteryService.drawLotteryWinner()).thenReturn(winner);
		when(connectInfoConverter.convert(winner)).thenReturn(winnerInfo);
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, winnerInfo))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.chooseLotteryMessage(null);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(lotteryService).drawLotteryWinner();
		verify(connectInfoConverter).convert(winner);
	}

	@Test
	@DisplayName("사용자 정보 조회")
	void infoMessage() {
		// given
		JandiWebhookRequest.Writer writer = new JandiWebhookRequest.Writer("id1", "name1", "email1", null);
		JandiWebhookRequest request = new JandiWebhookRequest("token", "team", "room", writer,
				"text", "data", "keyword", "created", "platform", "ip");

		JandiUserInfoRequest infoRequest = JandiUserInfoRequest.from(request);
		ConnectInfo userInfo = new ConnectInfo("사용자 정보", "name1 (email1)", null);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.OK.getMessage(),
				"successColor",
				List.of(userInfo)
		);

		when(connectInfoConverter.convert(infoRequest)).thenReturn(userInfo);
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, userInfo))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.infoMessage(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(connectInfoConverter).convert(infoRequest);
	}

	@Test
	@DisplayName("추첨 등록")
	void registerLotteryMessage() {
		// given
		LotteryRequest request = new LotteryRequest(1L, "추첨1", "position1");

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.CREATED.getMessage(),
				"successColor",
				List.of(new ConnectInfo(null, ResponseCode.CREATED.getMessage(), null))
		);

		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.CREATED))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.registerLotteryMessage(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(lotteryService).registerLottery(request);
	}

	@Test
	@DisplayName("추첨 목록 조회")
	void lotteryListMessage() {
		// given
		List<LotteryDetailResponse> lotteryDetailRespons = List.of(
				new LotteryDetailResponse("추첨1", "position1", LocalDateTime.now(), 1L, 1L),
				new LotteryDetailResponse("추첨2", "position2", LocalDateTime.now(), 2L, 1L)
		);

		List<LotteryView> lotteryViews = lotteryDetailRespons.stream()
				.map(LotteryView::from)
				.toList();

		LotteryViewList viewList = LotteryViewList.from(lotteryViews);

		List<ConnectInfo> connectInfoList = List.of(
				new ConnectInfo("추첨 목록", "추첨1 - position1\n추첨2 - position2", null)
		);

		JandiWebhookResponse expectedResponse = JandiWebhookResponse.of(
				ResponseCode.OK.getMessage(),
				"successColor",
				connectInfoList
		);

		when(connectInfoConverter.convertList(viewList)).thenReturn(connectInfoList);
		when(jandiResponseGenerator.createSuccessResponse(ResponseCode.OK, connectInfoList))
				.thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiMessageFactory.lotteryListMessage(lotteryDetailRespons);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(connectInfoConverter).convertList(viewList);
	}
}