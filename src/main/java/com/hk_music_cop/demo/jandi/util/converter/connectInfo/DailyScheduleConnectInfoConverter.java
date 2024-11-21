package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;

public interface DailyScheduleConnectInfoConverter extends ConnectInfoConverter<DailySchedule> {
	ConnectInfo convert(DailySchedule dailySchedule);
}
