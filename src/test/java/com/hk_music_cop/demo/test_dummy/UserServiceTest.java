package com.hk_music_cop.demo.test_dummy;

import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.external.jandi.JandiProperties;
import com.hk_music_cop.demo.test_dummy.domain.User;
import com.hk_music_cop.demo.test_dummy.mapper.TestMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private TestMapper testMapper;

	@Autowired
	private JandiProperties jandiProperties;
	@Autowired
	private GoogleSheetProperties googleSheetProperties;

	@Test
	void findAll() {
		List<User> all = testMapper.findAll();
		System.out.println(all);
	}

	@Test
	void findById() {
		User byId = testMapper.findById(1L);
		System.out.println(byId);
	}

	@Test
	void propertiesTest() {

		System.out.println("jandi : " + jandiProperties);

		System.out.println("google : " + googleSheetProperties.getSpreadsheetId());

	}
}
