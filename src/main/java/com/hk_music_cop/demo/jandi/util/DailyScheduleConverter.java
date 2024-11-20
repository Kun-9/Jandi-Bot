package com.hk_music_cop.demo.jandi.util;

import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.Todo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DailyScheduleConverter {

	public static JandiWebhookResponse.ConnectInfo toConnectInfo(DailySchedule dailySchedule) {
		if (dailySchedule == null) return null;

		String content = dailySchedule.getTodos().stream()
				.map(Todo::getTask)
				.collect(Collectors.joining("\n"));

		return new JandiWebhookResponse.ConnectInfo(
				dailySchedule.getDayName(),
				content.isEmpty() ? null : content,
				null
		);
	}
}
