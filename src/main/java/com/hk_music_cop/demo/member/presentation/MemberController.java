package com.hk_music_cop.demo.member.presentation;


import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.jwt.annotation.Token;
import com.hk_music_cop.demo.global.jwt.common.TokenType;
import com.hk_music_cop.demo.global.jwt.service.JwtTokenService;
import com.hk_music_cop.demo.global.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;
import com.hk_music_cop.demo.member.dto.request.JoinRequest;
import com.hk_music_cop.demo.member.dto.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenService jwtTokenService;

	@PostMapping("login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
		LoginResponse loginResponse = jwtTokenService.login(loginRequest);

		ApiResponse<LoginResponse> response = ApiResponse.of(ResponseCode.LOGIN_SUCCESS, loginResponse);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}


	@PostMapping("join")
	public ResponseEntity<ApiResponse<Void>> join(@Valid @RequestBody JoinRequest joinRequest) {
		memberService.join(joinRequest);

		ApiResponse<Void> response = ApiResponse.from(ResponseCode.MEMBER_JOIN_SUCCESS);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@PostMapping("logout")
	public ResponseEntity<ApiResponse<Void>> logout(@Token(type = TokenType.ACCESS) String accessToken) {
		jwtTokenService.logout(accessToken);

		ApiResponse<Void> response = ApiResponse.from(ResponseCode.LOGOUT_SUCCESS);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}
}
