package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import org.springframework.stereotype.Component;

@Component
public class SingleStringMessageConnectInfoConverterImpl implements SingleStringMessageConnectInfoConverter {


	@Override
	public boolean supports(Class<?> sourceType) {
		return String.class.isAssignableFrom(sourceType);
	}

	@Override
	public ConnectInfo convert(String message) {
		return ConnectInfo.fromDescription(message);
	}
	}
