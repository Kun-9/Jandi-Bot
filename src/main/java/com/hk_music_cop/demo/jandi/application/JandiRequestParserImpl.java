package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.domain.JandiRequestData;
import org.springframework.stereotype.Component;

import static com.hk_music_cop.demo.jandi.domain.JandiRequestData.*;

@Component
public class JandiRequestParserImpl implements JandiRequestParser {

	public Params getParameter(String requestData) {
		JandiRequestData jandiRequestData = new JandiRequestData();
		FormatValidate formatValidate = jandiRequestData.validate(requestData);

		return formatValidate.getValidParamCnt();
	}
}
