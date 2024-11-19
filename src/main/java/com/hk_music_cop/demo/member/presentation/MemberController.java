package com.hk_music_cop.demo.member.presentation;


import com.hk_music_cop.demo.ex.Token;
import com.hk_music_cop.demo.ex.TokenType;
import com.hk_music_cop.demo.global.security.jwt.JwtTokenService;
import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
		TokenResponse tokenResponse = jwtTokenService.login(loginRequest);

		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}


	@PostMapping("join")
	public ResponseEntity<String> join(@RequestBody MemberRequest memberRequest) {
		memberService.join(memberRequest);
		return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
	}

	@PostMapping("logout")
	public ResponseEntity<String> logout(@Token(type = TokenType.ACCESS) String accessToken, @Token(type = TokenType.REFRESH) String refreshToken) {
		boolean logout = jwtTokenService.logout(accessToken, refreshToken);

		return logout
				? new ResponseEntity<>("성공적으로 로그아웃 되었습니다.", HttpStatus.OK)
				: new ResponseEntity<>("로그아웃에 실패 하였습니다.", HttpStatus.BAD_REQUEST);
	}
}
