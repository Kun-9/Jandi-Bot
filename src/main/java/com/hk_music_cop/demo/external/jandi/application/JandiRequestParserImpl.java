//package com.hk_music_cop.demo.external.jandi.application;
//
//import com.hk_music_cop.demo.external.jandi.domain.JandiRequestData;
//import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
//import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
//import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
//import org.json.JSONObject;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import static com.hk_music_cop.demo.external.jandi.domain.JandiRequestData.*;
//
//@Component
//public class JandiRequestParserImpl implements JandiRequestParser {
//
//	public Params getParameter(String requestData) {
//		JandiRequestData jandiRequestData = new JandiRequestData();
//		FormatValidate formatValidate = jandiRequestData.validate(requestData);
//
//		return formatValidate.getValidParamCnt();
//	}
//
//	public List<String> parse(Params params) {
//
//		List<> result = new ArrayList<>();
//
//		switch (params.getCommand()) {
//			case "이번주 일정" -> {
//				LocalDate now = LocalDate.now();
//				result.add(now.getYear());
//				result.add(now.getMonthValue());
//				result.add(now.getDayOfMonth());
//			}
//			case "오늘 일정" -> response = jandiMessageFactory.scheduleDayMessage(LocalDate.now());
//			case "일단위 일정 조회" -> {
//				List<String> param = parameters.get(0);
//
//				LocalDate date = LocalDate.of(
//						Integer.parseInt(param.get(0)),
//						Integer.parseInt(param.get(1)),
//						Integer.parseInt(param.get(2))
//				);
//
//				response = jandiMessageFactory.scheduleDayMessage(date);
//			}
//			case "주단위 일정 조회" -> {
//				List<String> param = parameters.get(0);
//				response = jandiMessageFactory.scheduleWeekMessage(LocalDate.of(
//						Integer.parseInt(param.get(0)),
//						Integer.parseInt(param.get(1)),
//						(Integer.parseInt(param.get(2)) - 1) * 7 + 1
//				));
//			}
//			case "추첨" -> response = jandiMessageFactory.chooseLotteryMessage(null);
//			case "내 정보" -> response = jandiMessageFactory.infoMessage(request);
//			case "추첨 등록" -> {
//				List<String> registerInfo = parameters.get(0);
//				response = jandiMessageFactory.registerLotteryMessage(new LotteryRequest(memberId, registerInfo.get(0), registerInfo.get(1)));
//			}
//			case "추첨 삭제" -> {
//				String targetName = parameters.get(0).get(0);
//				response = jandiMessageFactory.deleteLotteryMessage(new LotteryRequest(memberId, targetName, null));
//			}
//			case "추첨 수정" -> {
//				String targetName = parameters.get(0).get(0);
//
//				LotteryResponse lotteryResponse = lotteryService.validateExistByName(targetName);
//
//				List<String> updateParam = parameters.get(1);
//				response = jandiMessageFactory.updateLotteryMessage(lotteryResponse.getLotteryId(), new LotteryRequest(memberId, updateParam.get(0), updateParam.get(1)));
//			}
//			default -> throw new JandiUndefinedCommand(params.getCommand());
//		}
//
//	}
//}
