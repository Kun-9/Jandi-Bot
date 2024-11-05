package com.hk_music_cop.demo.external.jandi.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class JandiRequestParserImplTest {

	@Autowired
	JandiRequestParser jandiRequestParser;


	@Test
	void parseLotteryRequest() {
		jandiRequestParser.parseLotteryRequest("[주단위 일정 조회] [2024,11,2]");
	}
}