package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.Todo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DailyScheduleConnectInfoConverterImpl implements DailyScheduleConnectInfoConverter {

	@Override
	public boolean supports(Class<?> sourceType) {
		return DailySchedule.class.isAssignableFrom(sourceType);
	}

	@Override
	public ConnectInfo convert(DailySchedule dailySchedule) {

		if (dailySchedule == null) return null;

		String content = dailySchedule.getTodos().stream()
				.map(Todo::getTask)
				.collect(Collectors.joining("\n"));

		return new ConnectInfo(
				dailySchedule.getDayName(),
				content.isEmpty() ? null : content,
				null
		);
	}
}
