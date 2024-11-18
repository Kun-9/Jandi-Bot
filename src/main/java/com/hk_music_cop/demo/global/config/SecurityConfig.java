package com.hk_music_cop.demo.global.config;

import com.hk_music_cop.demo.global.config.filter.JwtAuthenticationFilter;
import com.hk_music_cop.demo.global.error.ErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ErrorHandler errorHandler;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.addFilterBefore(
						jwtAuthenticationFilter,
						UsernamePasswordAuthenticationFilter.class
				)
				.authorizeHttpRequests(auth -> auth
								.requestMatchers("/jandi/**").permitAll()
								.requestMatchers("/api/lottery/**").permitAll()
								.requestMatchers("/api/member/**").permitAll()
								.requestMatchers("/api/schedule/**").permitAll()
								.requestMatchers("/auth/**").permitAll()
//						        .requestMatchers("/api/**").hasRole("ADMIN")
								.requestMatchers(HttpMethod.POST,"/api/lottery/**").authenticated()
								.requestMatchers(HttpMethod.POST,"/api/lottery/remove/**").authenticated()
								.anyRequest().authenticated()
				).exceptionHandling(exception -> {
					String message = "권한이 없습니다.";
					exception.authenticationEntryPoint(getAuthenticationEntryPoint(message));
				});

		return http.build();
	}

	private AuthenticationEntryPoint getAuthenticationEntryPoint(String message) {
		return (request, response, e) ->
				errorHandler.handleFilterExceptionMessage(response, e, HttpStatus.UNAUTHORIZED, message);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
