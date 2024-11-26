package com.hk_music_cop.demo.global.config;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.security.filter.JwtAuthenticationFilter;
import com.hk_music_cop.demo.global.common.error.ErrorHandler;
import com.hk_music_cop.demo.global.security.common.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
				.authorizeHttpRequests(auth -> auth
								.requestMatchers("/jandi/**").permitAll()
								.requestMatchers("/api/lottery/**").permitAll()
								.requestMatchers("/api/member/**").permitAll()
								.requestMatchers("/api/member/join").permitAll()
								.requestMatchers("/api/schedule/**").permitAll()
								.requestMatchers("/auth/**").permitAll()
						        .requestMatchers("/api/manager/**").hasRole(Role.getRoleName(Role.ROLE_MANAGER))
								.requestMatchers("/api/lottery/winner").permitAll()
								.requestMatchers(HttpMethod.POST,"/api/lottery/remove/**").authenticated()
								.requestMatchers(HttpMethod.POST,"/api/lottery/**").authenticated()
								.anyRequest().authenticated()
				)
				.addFilterBefore(
						jwtAuthenticationFilter,
						UsernamePasswordAuthenticationFilter.class
				)
				.exceptionHandling(exception -> exception.authenticationEntryPoint(getAuthenticationEntryPoint()));

		return http.build();
	}

	private AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return (request, response, e) ->
				errorHandler.handleExceptionDirect(response, e, ResponseCode.UNAUTHORIZED);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
