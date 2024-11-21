package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.request.JandiUserInfoRequest;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;

public interface UserInfoConnectInfoConverter extends ConnectInfoConverter<JandiUserInfoRequest> {
	ConnectInfo convert(JandiUserInfoRequest jandiUserInfoRequest);
}
