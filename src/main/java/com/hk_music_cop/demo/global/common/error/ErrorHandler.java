package com.hk_music_cop.demo.global.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler {

	private final ObjectMapper objectMapper;


	// 모든 에러 처리
	public ApiResponse<?> handleException(Exception e, ResponseCode code) {
		log.error("error occurred: ", e);

		return ApiResponse.from(code);
	}

	// Custom 에러 처리
	public ApiResponse<?> handleCustomException(CustomException e) {
		log.error("error occurred: ", e);

		return ApiResponse.from(e);
	}

	public ApiResponse<?> handleCustomExceptionWithMessage(CustomException e, String message) {
		log.error("error occurred: ", e);

		return ApiResponse.error(e.getResponseCode(), message);
	}

//	public ApiResponse<?> handleExceptionWithMessage(Exception e, ResponseCode code) {
//		log.error("error occurred: ", e);
//
//		return ApiResponse.of(code);
//	}

	public void handleExceptionDirect(
			HttpServletResponse response,
			Exception e,
			ResponseCode code
	) throws IOException {
		ApiResponse<?> apiResponse = handleException(e, code);

		response.setStatus(apiResponse.getStatus());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		objectMapper.writeValue(response.getOutputStream(), apiResponse);
	}

}
