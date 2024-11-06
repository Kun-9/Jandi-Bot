package com.hk_music_cop.demo.global.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private final int status;
	private final String message;
	private final T data;

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(200, "success", data);
	}

	public static <T> ApiResponse<T> error(int status, String message) {
		return new ApiResponse<>(status, message, null);
	}
}
