package com.hk_music_cop.demo.jandi.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hk_music_cop.demo.jandi.application.DailyScheduleConverter;
import com.hk_music_cop.demo.lottery.dto.LotterySimple;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record JandiWebhookResponse(String body, String connectColor, List<ConnectInfo> connectInfo) {

	public static JandiWebhookResponse withoutConnectInfo(String body, String connectColor) {
		return new JandiWebhookResponse(body, connectColor, null);
	}

	public static JandiWebhookResponse of(String body, String connectColor, List<ConnectInfo> connectInfo) {
		return new JandiWebhookResponse(body, connectColor, connectInfo);
	}

	public JandiWebhookResponse withConnectInfoList(List<ConnectInfo> connectInfoList) {
		return new JandiWebhookResponse(this.body, this.connectColor, connectInfoList);
	}

	public JandiWebhookResponse withConnectInfo(ConnectInfo connectInfo) {
		return new JandiWebhookResponse(body, connectColor, Collections.singletonList(connectInfo));
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record ConnectInfo(String title, String description, String imageUrl) {

		public static ConnectInfo withOutImg(String title, String description) {
			return new ConnectInfo(title, description, null);
		}

		// 단순 [이름 포지션] 을 내용으로 가지는 ConnectInfo 로 변경
		public static ConnectInfo fromLotteryInfo(LotteryResponse lotteryResponse) {
			return ConnectInfo.withOutImg(LotterySimple.from(lotteryResponse).getLotteryStrings(), null);
		}

		public static ConnectInfo from(DailySchedule dailySchedule) {
			return DailyScheduleConverter.toConnectInfo(dailySchedule);
		}

		public static List<ConnectInfo> from(WeeklySchedule weeklySchedule) {
			if (weeklySchedule.isEmpty()) return Collections.emptyList();

			// null이 아닌 요소들을 ConnectInfo 로 변환 후 리스트로 저장
			return weeklySchedule.getDailySchedules()
					.stream().filter(Objects::nonNull)
					.map(ConnectInfo::from)
					.toList();
		}

		public static ConnectInfo fromLotteryWinner(LotteryResponse winner) {
			return ConnectInfo.withOutImg(
					"결과",
					String.format("%s님 당첨되었습니다.\n축하합니다~!", winner.getLotteryName())
			);
		}

		public static ConnectInfo ofSingleStringData(String content) {
			return ConnectInfo.withOutImg(
					null,
					content
			);
		}
	}
}
