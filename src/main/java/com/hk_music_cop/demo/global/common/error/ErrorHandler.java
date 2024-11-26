package com.hk_music_cop.demo.global.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.global.common.response.ErrorResponse;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler {

	private final ObjectMapper objectMapper;

	// 모든 에러 처리
	public ErrorResponse<Void> handleException(Exception e, ErrorCode code) {
		log.error("error occurred: ", e);

		return ErrorResponse.from(code);
	}

	// Custom 에러 처리
	public ErrorResponse<?> handleCustomException(CustomException e) {
		log.error("error occurred: ", e);

		return ErrorResponse.from(e);
	}

	public ErrorResponse<Void> handleCustomExceptionWithMessage(CustomException e) {
		log.error("error occurred: ", e);

		return ErrorResponse.from(e);
	}

	public ErrorResponse<List<ValidationError>> handleValidationException(
			MethodArgumentNotValidException e
	) {
		log.error("error occurred: ", e);

		return ErrorResponse.validationError(e);
	}

	public void handleExceptionDirect(
			HttpServletResponse response,
			Exception e,
			ErrorCode code
	) throws IOException {
		log.error("error occurred: ", e);

		ErrorResponse<?> errorResponse = handleException(e, code);

		response.setStatus(errorResponse.status());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		objectMapper.writeValue(response.getOutputStream(), errorResponse);
	}

}
