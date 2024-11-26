package com.hk_music_cop.demo.global.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationError {
	private final String field;

	@Nullable
	private final Object rejectedValue;
	private final String defaultMessage;

	public static ValidationError from(FieldError fieldError) {
		return new ValidationError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}

	public static List<ValidationError> from(List<FieldError> fieldErrors) {
		return fieldErrors.stream()
				.map(ValidationError::from)
				.toList();
	}
}
