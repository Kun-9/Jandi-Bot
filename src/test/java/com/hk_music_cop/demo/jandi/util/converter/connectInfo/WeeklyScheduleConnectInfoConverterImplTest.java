package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.schedule.domain.DailySchedule;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeeklyScheduleConnectInfoConverterImplTest {

	@Autowired
	private ConnectInfoConverterComposite converter;

//	@Autowired
//	private ConnectInfoListConverter connectInfoListConverter;

	@Test
	void convert() {

		DailySchedule daily = new DailySchedule(DayOfWeek.FRIDAY, List.of("hello"));


		ConnectInfo connectInfo = converter.convert(daily);

		System.out.println(connectInfo.toString());


		WeeklySchedule week = new WeeklySchedule(Collections.singletonList(daily), LocalDate.now());

//		ConnectInfo connectInfo = connectInfoListConverter.convertToList(daily);

//		System.out.println(connectInfo.toString());

//		assertNotNull(connectInfo);
	}
}