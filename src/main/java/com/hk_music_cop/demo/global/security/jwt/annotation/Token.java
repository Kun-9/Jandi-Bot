package com.hk_music_cop.demo.global.security.jwt.annotation;

import com.hk_music_cop.demo.global.security.jwt.common.TokenType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	boolean required() default true;
	TokenType type();
}
