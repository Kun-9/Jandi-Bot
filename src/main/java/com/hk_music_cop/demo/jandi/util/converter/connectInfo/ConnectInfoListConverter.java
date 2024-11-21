package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;

import java.util.List;

public interface ConnectInfoListConverter<T> extends BaseConverter {
	List<ConnectInfo> convertToList(T source);
}
