package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class WeeklyScheduleConnectInfoConverterImpl implements WeeklyScheduleConnectInfoConverter {

	private final ConnectInfoConverter<DailySchedule> converter;

	@Override
	public boolean supports(Class<?> sourceType) {
		return WeeklySchedule.class.isAssignableFrom(sourceType);
	}

	@Override
	public List<ConnectInfo> convertToList(WeeklySchedule weeklySchedule) {
		if (weeklySchedule.isEmpty()) return Collections.emptyList();

		// null이 아닌 요소들을 ConnectInfo 로 변환 후 리스트로 저장
		return weeklySchedule.getDailySchedules().stream()
				.filter(Objects::nonNull)
				.map(converter::convert)
				.toList();
	}
}
