package com.hk_music_cop.demo.lottery.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jdk.jfr.Unsigned;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

	private Class<? extends Enum> enumClass;

	@Override
	public void initialize(EnumValidator annotation) {
		enumClass = annotation.enumClass();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// null 허용시
		if (value == null) {
			return true;
		}

		try {
			// 해당 Enum 클래스에 밸류 값 대입 후 오류 발생 여부를 확인
			Enum.valueOf((Class<? extends Enum>) enumClass, value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
