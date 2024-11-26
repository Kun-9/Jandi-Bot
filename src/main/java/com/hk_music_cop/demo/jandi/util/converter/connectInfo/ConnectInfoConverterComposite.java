package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConnectInfoConverterComposite {

	private final List<ConnectInfoConverter<?>> singleConverters;
	private final List<ConnectInfoListConverter<?>> listConverters;

	@SuppressWarnings("Unchecked")
	public <T> ConnectInfo convert(T source) {
		return singleConverters.stream()
				.filter(converter -> converter.supports(source.getClass()))
				.findFirst()
				.map(converter -> ((ConnectInfoConverter<T>) converter).convert(source))
				.orElseThrow(() -> new CustomException(ErrorCode.CONVERTER_NOT_SUPPORT));
	}


	@SuppressWarnings("Unchecked")
	public <T> List<ConnectInfo> convertList(T source) {
		return listConverters.stream()
				.filter(converter -> converter.supports(source.getClass()))
				.findFirst()
				.map(converter -> ((ConnectInfoListConverter<T>) converter).convertToList(source))
				.orElseThrow(() -> new CustomException(ErrorCode.CONVERTER_NOT_SUPPORT));
	}
}
