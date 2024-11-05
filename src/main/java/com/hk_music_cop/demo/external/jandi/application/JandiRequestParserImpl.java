package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.external.jandi.domain.JandiRequestData;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hk_music_cop.demo.external.jandi.domain.JandiRequestData.*;

@Component
public class JandiRequestParserImpl implements JandiRequestParser {

	@Override
	public void parseLotteryRequest(String requestData) {

		Params params = getParameter(requestData);

		String command = params.getCommand();
		List<List<String>> parameters = params.getParameters();

	}

	public Params getParameter(String requestData) {
		JandiRequestData jandiRequestData = new JandiRequestData();
		FormatValidate formatValidate = jandiRequestData.validate(requestData);

		return formatValidate.getValidParamCnt();
	}

	@Override
	public void parseScheduleRequest(String requestData) {

	}

	@Override
	public void parseMemberRequest(String requestData) {

	}
}
