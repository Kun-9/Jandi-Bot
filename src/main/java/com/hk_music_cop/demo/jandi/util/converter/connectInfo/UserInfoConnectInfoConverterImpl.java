package com.hk_music_cop.demo.jandi.util.converter.connectInfo;

import com.hk_music_cop.demo.jandi.dto.request.JandiUserInfoRequest;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConnectInfoConverterImpl implements UserInfoConnectInfoConverter {

	@Override
	public boolean supports(Class<?> sourceType) {
		return JandiUserInfoRequest.class.isAssignableFrom(sourceType);
	}

	@Override
	public ConnectInfo convert(JandiUserInfoRequest jandiUserInfoRequest) {


		String writerInfo = "일자 : " + jandiUserInfoRequest.getCreatedAt() + "\n" +
				"ID : " + jandiUserInfoRequest.getWriter().getId() + "\n" +
				"이름 : " + jandiUserInfoRequest.getWriter().getName() + "\n" +
				"E-Mail : " + jandiUserInfoRequest.getWriter().getEmail() + "\n" +
				"TEL : " + jandiUserInfoRequest.getWriter().getPhoneNumber() + "\n" +
				"키워드 : " + jandiUserInfoRequest.getKeyword() + "\n" +
				"IP : " + jandiUserInfoRequest.getIp() + "\n" +
				"요청 방 이름 : " + jandiUserInfoRequest.getRoomName() + "\n" +
				"팀 이름 : " + jandiUserInfoRequest.getTeamName() + "\n" +
				"DATA : " + jandiUserInfoRequest.getData() + "\n" +
				"TEXT : " + jandiUserInfoRequest.getText() + "\n" +
				"토큰 : " + jandiUserInfoRequest.getToken() + "\n" +
				"플랫폼 : " + jandiUserInfoRequest.getPlatform() + "\n";

		return ConnectInfo.fromDescription(writerInfo);
	}
}
