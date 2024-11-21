package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;

public interface ConnectInfoConverter<T> extends BaseConverter {
	ConnectInfo convert(T source);
}
