package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.response.ConnectInfo;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;

import java.util.List;

/**
 * 잔디 웹훅 응답 생성을 위한 인터페이스
 */
public interface JandiResponseGenerator {

	/**
	 * 성공 응답 생성 (단일 ConnectInfo)
	 * @param responseCode 응답 코드
	 * @param connectInfo 연결 정보
	 * @return 잔디 웹훅 응답
	 */
	JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, ConnectInfo connectInfo);

	/**
	 * 성공 응답 생성 (ConnectInfo 리스트)
	 * @param responseCode 응답 코드
	 * @param connectInfoList 연결 정보 리스트
	 * @return 잔디 웹훅 응답
	 */
	JandiWebhookResponse createSuccessResponse(ResponseCode responseCode, List<ConnectInfo> connectInfoList);

	/**
	 * 성공 응답 생성 (ResponseCode만 사용)
	 * @param responseCode 응답 코드
	 * @return 잔디 웹훅 응답
	 */
	JandiWebhookResponse createSuccessResponse(ResponseCode responseCode);


}
