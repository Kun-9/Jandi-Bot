package com.hk_music_cop.demo.global.jwt.annotation;

import com.hk_music_cop.demo.global.jwt.common.TokenType;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomEmptyTokenException;
import com.hk_music_cop.demo.global.jwt.util.TokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class TokenResolver implements HandlerMethodArgumentResolver {

	private final TokenExtractor tokenExtractor;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Token.class);
	}

	@Override
	public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		Token tokenAnnotation = parameter.getParameterAnnotation(Token.class);
		String token;

		if (tokenAnnotation.type() == TokenType.ACCESS) {
			token = tokenExtractor.extractAccessTokenFromRequest(request);
		} else {
			token = tokenExtractor.extractRefreshTokenFromRequest(request);
		}

		if (token == null || token.isBlank()) {
			throw new CustomEmptyTokenException("'" + tokenAnnotation.type().name() + " token'이 없습니다.");
		}

		return token;
	}
}
