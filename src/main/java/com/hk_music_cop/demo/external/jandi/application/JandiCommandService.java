package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
import com.hk_music_cop.demo.global.error.jandi.LotteryNotFoundException;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.hk_music_cop.demo.external.jandi.domain.JandiRequestData.*;

@RequiredArgsConstructor
@Service
public class JandiCommandService {

	private final LotteryRepository lotteryRepository;
	JandiMessageFactory jandiMessageFactory;
	JandiRequestParser jandiRequestParser;
	MemberService memberService;


	public JSONObject executeCommand(JandiWebhookRequest request) {

		Params params = jandiRequestParser.getParameter(request.getData());
		List<List<String>> parameters = params.getParameters();


		boolean exist = memberService.isUserIdExist(request.getWriter().getEmail());

		// 가입되어있지 않으면 가입 진행
		MemberRequest currentUser = new MemberRequest(request.getWriter().getName(), request.getWriter().getEmail(), request.getToken());

		if (!exist) {
			memberService.join(currentUser);
		}

		MemberResponse loginMember = memberService.login(currentUser);

		Long memberId = loginMember.getMemberId();

		// 토큰 처리 ////////////


		////////////////////

		JSONObject response;

		switch (params.getCommand()) {
			case "이번주 일정" -> response = jandiMessageFactory.scheduleWeekMessage(LocalDate.now());
			case "오늘 일정" -> response = jandiMessageFactory.scheduleDayMessage(LocalDate.now());
			case "일단위 일정 조회" -> {
				List<String> param = parameters.get(0);
				response = jandiMessageFactory.scheduleDayMessage(
						LocalDate.of(
								Integer.parseInt(param.get(0)),
								Integer.parseInt(param.get(1)),
								Integer.parseInt(param.get(2))
						)
				);
			}
			case "주단위 일정 조회" -> {
				List<String> param = parameters.get(0);
				response = jandiMessageFactory.scheduleWeekMessage(LocalDate.of(
						Integer.parseInt(param.get(0)),
						Integer.parseInt(param.get(1)),
						Integer.parseInt(param.get(2))
				));
			}
			case "추첨" -> response = jandiMessageFactory.chooseLotteryMessage(null);
			case "내 정보" -> response = jandiMessageFactory.infoMessage(request);
			case "추첨 등록" -> {
				List<String> registerInfo = parameters.get(0);
				response = jandiMessageFactory.registerLotteryMessage(new LotteryRequest(memberId, registerInfo.get(0), registerInfo.get(1)));
			}
			case "추첨 삭제" -> {
				String targetName = parameters.get(0).get(0);
				response = jandiMessageFactory.deleteLotteryMessage(new LotteryRequest(memberId, targetName, null));
			}
			case "추첨 수정" -> {
				String targetName = parameters.get(0).get(0);
				LotteryResponse lotteryResponse = lotteryRepository.findByName(targetName).orElseThrow(
						() -> new LotteryNotFoundException(targetName)
				);

				List<String> updateParam = parameters.get(1);
				response = jandiMessageFactory.updateLotteryMessage(lotteryResponse.getLotteryId(), new LotteryRequest(memberId, updateParam.get(0), updateParam.get(1)));
			}
			default -> throw new JandiUndefinedCommand(request.getData());
		}

		return response;
	}
}
