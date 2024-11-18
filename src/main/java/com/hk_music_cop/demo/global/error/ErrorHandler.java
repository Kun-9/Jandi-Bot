package com.hk_music_cop.demo.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk_music_cop.demo.global.error.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler {

	private final ObjectMapper objectMapper;

	public ErrorResponse handleException(Exception e, HttpStatus status) {
		log.error("error occurred: ", e);

		return ErrorResponse.of(
				status.value(),
				e.getMessage()
		);
	}

	public ErrorResponse handleExceptionMessage(Exception e, HttpStatus status, String message) {
		log.error("error occurred: ", e);

		return ErrorResponse.of(
				status.value(),
				message
		);
	}

	public void handleFilterException(
			HttpServletResponse response,
			Exception e,
			HttpStatus status
	) throws IOException {

		ErrorResponse errorResponse = handleExceptionMessage(e, status, e.getMessage());

		response.setStatus(errorResponse.status());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());


		objectMapper.writeValue(response.getOutputStream(), errorResponse);
	}

}
