package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.domain.JandiCommandParser;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomUndefinedCommand;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.hk_music_cop.demo.jandi.domain.JandiCommandParser.*;

@RequiredArgsConstructor
@Service
public class JandiCommandServiceImpl implements JandiCommandService {

	private final LotteryService lotteryService;
	private final JandiMessageFactory jandiMessageFactory;
	private final MemberService memberService;


	public JandiWebhookResponse executeCommand(JandiWebhookRequest request) {
		// 넘어온 문자열을 파라미터로 파싱 및 검증
		FormatValidate formatValidate = validate(request.getData());

		// 검증된 파라미터 객체 할당
		Params params = formatValidate.getValidParamCnt();
		List<List<String>> parameters = params.getParameters();

		// 요청자 이메일로 회원 여부 확인
		boolean exist = memberService.validationUserIdExist(request.getWriter().getEmail());

		// Json 으로 넘어온 사용자 정보로 멤버 객체 생성
		MemberRequest currentUser = new MemberRequest(request.getWriter().getName(), request.getWriter().getEmail(), request.getToken());

		if (!exist) {
			memberService.join(currentUser);
		}

		// 로그인
		MemberResponse loginMember = memberService.jandiLogin(currentUser);

		Long memberId = loginMember.getMemberId();

		JandiWebhookResponse response;

		switch (params.getCommand()) {
			case "이번주 일정" -> response = jandiMessageFactory.scheduleWeekMessage(LocalDate.now());
			case "오늘 일정" -> response = jandiMessageFactory.scheduleDayMessage(LocalDate.now());
			case "일단위 일정 조회" -> {
				List<String> param = parameters.get(0);

				LocalDate date = LocalDate.of(
						Integer.parseInt(param.get(0)),
						Integer.parseInt(param.get(1)),
						Integer.parseInt(param.get(2))
				);
				response = jandiMessageFactory.scheduleDayMessage(date);
			}
			case "주단위 일정 조회" -> {
				List<String> param = parameters.get(0);
				response = jandiMessageFactory.scheduleWeekMessage(
						LocalDate.of(
								Integer.parseInt(param.get(0)),
								Integer.parseInt(param.get(1)),
								parseDayToNthWeek(Integer.parseInt(param.get(2)))
						)
				);
			}
			case "추첨" -> response = jandiMessageFactory.chooseLotteryMessage(null);
			case "내 정보" -> response = jandiMessageFactory.infoMessage(request);
			case "추첨 등록" -> {
				List<String> registerInfo = parameters.get(0);
				response = jandiMessageFactory.registerLotteryMessage(
						new LotteryRequest(memberId, registerInfo.get(0), registerInfo.get(1))
				);
			}
			case "추첨 삭제" -> {
				String targetName = parameters.get(0).get(0);
				response = jandiMessageFactory.deleteLotteryMessage(
						new LotteryRequest(memberId, targetName, null)
				);
			}
			case "추첨 수정" -> {
				String targetName = parameters.get(0).get(0);

				List<String> updateParam = parameters.get(1);

				LotteryUpdateRequest lotteryUpdateRequest = LotteryUpdateRequest.of(targetName, updateParam.get(0), updateParam.get(1));

				response = jandiMessageFactory.updateLotteryMessage(
						memberId,
						lotteryUpdateRequest
				);
			}
			case "추첨 리스트 조회" -> {
				List<LotteryResponse> allLottery = lotteryService.getAllLottery();
				response = jandiMessageFactory.lotteryListMessage(allLottery);
			}
			default -> throw new CustomUndefinedCommand(params.getCommand());
		}

		return response;
	}

	private int parseDayToNthWeek(int day) {
		return (day - 1) * 7 + 1;
	}
}
