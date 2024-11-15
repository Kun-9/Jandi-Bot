package com.hk_music_cop.demo.member.presentation;


import com.hk_music_cop.demo.global.security.jwt.JwtTokenProvider;
import com.hk_music_cop.demo.global.security.jwt.dto.TokenResponse;
import com.hk_music_cop.demo.member.application.MemberService;
import com.hk_music_cop.demo.member.dto.request.LoginRequest;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {

		String userId = loginRequest.userId();
		String password = loginRequest.password();

		log.info("userId: {}, password: {}", userId, password);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userId, password)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenResponse tokenResponse = jwtTokenProvider.createToken(authentication);


		return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	}


	@PostMapping("join")
	public ResponseEntity<String> join(@RequestBody MemberRequest memberRequest) {
		memberService.join(memberRequest);
		return new ResponseEntity<>("회원가입이 완료되었습니다.",HttpStatus.OK);
	}
}
