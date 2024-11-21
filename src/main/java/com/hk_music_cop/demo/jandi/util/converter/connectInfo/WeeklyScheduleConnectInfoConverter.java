package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;

import java.util.List;

public interface WeeklyScheduleConnectInfoConverter extends ConnectInfoListConverter<WeeklySchedule> {
	List<ConnectInfo> convertToList(WeeklySchedule weeklySchedule);
}
