package com.hk_music_cop.demo.unit.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomUndefinedCommand;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.application.JandiCommandServiceImpl;
import com.hk_music_cop.demo.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookRequest;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JandiCommandServiceImplTest {

	@Mock
	private LotteryService lotteryService;
	@Mock
	private JandiMessageFactory jandiMessageFactory;
	@Mock
	private MemberService memberService;

	@InjectMocks
	private JandiCommandServiceImpl jandiCommandService;

	private static final MemberResponse LOGIN_MEMBER = MemberResponse.builder()
			.memberId(1L)
			.build();

	@Test
	@DisplayName("신규 사용자의 이번주 일정 조회 테스트")
	void executeCommand_ThisWeekSchedule_NewUser() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "newUser", "new@email.com", null),
				"[이번주 일정]"
		);

		ConnectInfo connectInfo = new ConnectInfo("이번주 일정", "일정 내용", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("주간 일정", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("new@email.com")).thenReturn(false);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.scheduleWeekMessage(any(LocalDate.class))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(memberService).join(any(MemberRequest.class));
		verify(jandiMessageFactory).scheduleWeekMessage(any(LocalDate.class));
	}

	@Test
	@DisplayName("기존 사용자의 오늘 일정 조회 테스트")
	void executeCommand_TodaySchedule_ExistingUser() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[오늘 일정]"
		);

		ConnectInfo connectInfo = new ConnectInfo("오늘 일정", "일정 내용", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("일일 일정", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.scheduleDayMessage(any(LocalDate.class))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(memberService, never()).join(any());
		verify(jandiMessageFactory).scheduleDayMessage(any(LocalDate.class));
	}

	@Test
	@DisplayName("일단위 일정 조회 테스트")
	void executeCommand_SpecificDaySchedule() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[일단위 일정 조회][2024,3,15]"
		);

		ConnectInfo connectInfo = new ConnectInfo("특정일 일정", "일정 내용", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("일일 일정", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.scheduleDayMessage(LocalDate.of(2024, 3, 15))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).scheduleDayMessage(LocalDate.of(2024, 3, 15));
	}

	@Test
	@DisplayName("주단위 일정 조회 테스트")
	void executeCommand_WeeklySchedule() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[주단위 일정 조회][2024,3,2]"  // 2주차
		);

		ConnectInfo connectInfo = new ConnectInfo("주간 일정", "일정 내용", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("주간 일정", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.scheduleWeekMessage(LocalDate.of(2024, 3, 8))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).scheduleWeekMessage(LocalDate.of(2024, 3, 8));
	}

	@Test
	@DisplayName("추첨 실행 테스트")
	void executeCommand_DrawLottery() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[추첨]"
		);

		ConnectInfo connectInfo = new ConnectInfo("추첨 결과", "당첨자 정보", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("추첨", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.chooseLotteryMessage(null)).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).chooseLotteryMessage(null);
	}

	@Test
	@DisplayName("내 정보 조회 테스트")
	void executeCommand_MyInfo() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[내 정보]"
		);

		ConnectInfo connectInfo = new ConnectInfo("사용자 정보", "상세 정보", null);
		JandiWebhookResponse expectedResponse = new JandiWebhookResponse("내 정보", "#00FF00", List.of(connectInfo));

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.infoMessage(request)).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).infoMessage(request);
	}

	@Test
	@DisplayName("추첨 등록 테스트")
	void executeCommand_RegisterLottery() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[추첨 등록][테스트추첨,기타]"
		);

		JandiWebhookResponse expectedResponse = new JandiWebhookResponse(
				ResponseCode.CREATED.getMessage(),
				"#00FF00",
				List.of(new ConnectInfo(null, ResponseCode.CREATED.getMessage(), null))
		);

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.registerLotteryMessage(any(LotteryRequest.class))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).registerLotteryMessage(argThat(req ->
				req.memberId().equals(1L) &&
						req.lotteryName().equals("테스트추첨") &&
						req.position().equals("기타")
		));
	}

	@Test
	@DisplayName("추첨 삭제 테스트")
	void executeCommand_DeleteLottery() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[추첨 삭제][테스트추첨]"
		);

		JandiWebhookResponse expectedResponse = new JandiWebhookResponse(
				ResponseCode.DELETED.getMessage(),
				"#00FF00",
				List.of(new ConnectInfo(null, ResponseCode.DELETED.getMessage(), null))
		);

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.deleteLotteryMessage(any(LotteryRequest.class))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).deleteLotteryMessage(argThat(req ->
				req.memberId().equals(1L) &&
						req.lotteryName().equals("테스트추첨") &&
						req.position() == null
		));
	}

	@Test
	@DisplayName("추첨 수정 테스트")
	void executeCommand_UpdateLottery() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[추첨 수정][기존추첨][새이름,새설명]"
		);

		JandiWebhookResponse expectedResponse = new JandiWebhookResponse(
				ResponseCode.UPDATED.getMessage(),
				"#00FF00",
				List.of(new ConnectInfo(null, ResponseCode.UPDATED.getMessage(), null))
		);

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(jandiMessageFactory.updateLotteryMessage(anyLong(), any(LotteryUpdateRequest.class))).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(jandiMessageFactory).updateLotteryMessage(
				eq(1L),
				argThat(req ->
						req.targetName().equals("기존추첨") &&
								req.name().equals("새이름") &&
								req.position().equals("새설명")
				)
		);
	}

	@Test
	@DisplayName("추첨 리스트 조회 테스트")
	void executeCommand_GetLotteryList() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[추첨 리스트 조회]"
		);

		List<LotteryResponse> lotteryResponses = List.of(
				new LotteryResponse("추첨1", "포지션1", LocalDateTime.now(), 1L, 1L),
				new LotteryResponse("추첨2", "포지션2", LocalDateTime.now(), 2L, 1L)
		);

		JandiWebhookResponse expectedResponse = new JandiWebhookResponse(
				ResponseCode.OK.getMessage(),
				"#00FF00",
				List.of(new ConnectInfo("추첨 목록", "현재 등록된 추첨 목록입니다", null))
		);

		when(memberService.validationUserIdExist("user@email.com")).thenReturn(true);
		when(memberService.jandiLogin(any())).thenReturn(LOGIN_MEMBER);
		when(lotteryService.getAllLottery()).thenReturn(lotteryResponses);
		when(jandiMessageFactory.lotteryListMessage(lotteryResponses)).thenReturn(expectedResponse);

		// when
		JandiWebhookResponse response = jandiCommandService.executeCommand(request);

		// then
		assertThat(response).isEqualTo(expectedResponse);
		verify(lotteryService).getAllLottery();
		verify(jandiMessageFactory).lotteryListMessage(lotteryResponses);
	}

	@Test
	@DisplayName("잘못된 명령어 형식 테스트")
	void executeCommand_InvalidCommandFormat() {
		// given
		JandiWebhookRequest request = createRequest(
				new JandiWebhookRequest.Writer("id1", "user", "user@email.com", null),
				"[잘못된명령어"  // 닫는 대괄호 없음
		);

		// when & then
		assertThatThrownBy(() -> jandiCommandService.executeCommand(request))
				.isInstanceOf(CustomUndefinedCommand.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.UNDEFINED_COMMAND);
	}

	private JandiWebhookRequest createRequest(JandiWebhookRequest.Writer writer, String data) {
		return new JandiWebhookRequest(
				"testToken",
				"teamName",
				"roomName",
				writer,
				"text",
				data,
				"keyword",
				"createdAt",
				"platform",
				"ip"
		);
	}
}