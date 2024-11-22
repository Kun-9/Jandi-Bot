package com.hk_music_cop.demo.unit.member.application;

import com.hk_music_cop.demo.global.common.error.exceptions.*;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.member.application.MemberServiceImpl;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberServiceImpl memberService;

	@Test
	@DisplayName("회원 가입 - 성공")
	void join_Success() {
		// given
		MemberRequest request = new MemberRequest("testName", "testId", "password");

		when(memberRepository.userIdExistValidation("testId")).thenReturn(false);
		when(memberRepository.usernameExistValidation("testName")).thenReturn(false);
		when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
		when(memberRepository.join(any(MemberRequest.class))).thenReturn(1L);

		// when
		Long memberId = memberService.join(request);

		// then
		assertThat(memberId).isEqualTo(1L);
		verify(memberRepository).join(argThat(memberRequest ->
				memberRequest.getName().equals("testName") &&
						memberRequest.getUserId().equals("testId") &&
						memberRequest.getPassword().equals("encodedPassword")
		));
	}

	@Test
	@DisplayName("회원 가입 - 중복 ID")
	void join_DuplicateUserId() {
		// given
		MemberRequest request = new MemberRequest("testName", "duplicateId", "password");
		when(memberRepository.userIdExistValidation("duplicateId")).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> memberService.join(request))
				.isInstanceOf(CustomDuplicatedUserIdException.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.DUPLICATE_USER_ID);
	}

	@Test
	@DisplayName("회원 가입 - 중복 이름")
	void join_DuplicateUsername() {
		// given
		MemberRequest request = new MemberRequest("duplicateName", "testId", "password");
		when(memberRepository.userIdExistValidation("testId")).thenReturn(false);
		when(memberRepository.usernameExistValidation("duplicateName")).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> memberService.join(request))
				.isInstanceOf(CustomDuplicatedNameException.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.DUPLICATE_NAME);
	}

	@Test
	@DisplayName("잔디 로그인 - 성공")
	void jandiLogin_Success() {
		// given
		MemberRequest request = new MemberRequest("testName", "testId", "password");
		MemberResponse response = MemberResponse.builder()
				.memberId(1L)
				.userId("testId")
				.password("password")
				.name("testName")
				.build();

		when(memberRepository.userIdExistValidation("testId")).thenReturn(true);
		when(memberRepository.findByUserId("testId")).thenReturn(response);

		// when
		MemberResponse result = memberService.jandiLogin(request);

		// then
		assertThat(result).isEqualTo(response);
	}

	@Test
	@DisplayName("잔디 로그인 - 존재하지 않는 사용자")
	void jandiLogin_UnknownMember() {
		// given
		MemberRequest request = new MemberRequest("testName", "unknownId", "password");
		when(memberRepository.userIdExistValidation("unknownId")).thenReturn(false);

		// when & then
		assertThatThrownBy(() -> memberService.jandiLogin(request))
				.isInstanceOf(CustomUnknownMemberException.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.UNKNOWN_MEMBER);
	}

	@Test
	@DisplayName("잔디 로그인 - 잘못된 비밀번호")
	void jandiLogin_IncorrectPassword() {
		// given
		MemberRequest request = new MemberRequest("testName", "testId", "wrongPassword");
		MemberResponse response = MemberResponse.builder()
				.memberId(1L)
				.userId("testId")
				.password("correctPassword")
				.name("testName")
				.build();

		when(memberRepository.userIdExistValidation("testId")).thenReturn(true);
		when(memberRepository.findByUserId("testId")).thenReturn(response);

		// when & then
		assertThatThrownBy(() -> memberService.jandiLogin(request))
				.isInstanceOf(CustomException.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.LOGIN_FAIL);
	}

	@Test
	@DisplayName("멤버 ID로 조회")
	void findByMemberId() {
		// given
		Long memberId = 1L;
		MemberResponse expected = MemberResponse.builder()
				.memberId(memberId)
				.userId("testId")
				.name("testName")
				.build();

		when(memberRepository.findByMemberId(memberId)).thenReturn(expected);

		// when
		MemberResponse result = memberService.findByMemberId(memberId);

		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("사용자 ID로 조회")
	void findByUserId() {
		// given
		String userId = "testId";
		MemberResponse expected = MemberResponse.builder()
				.memberId(1L)
				.userId(userId)
				.name("testName")
				.build();

		when(memberRepository.findByUserId(userId)).thenReturn(expected);

		// when
		MemberResponse result = memberService.findByUserId(userId);

		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("사용자 ID 존재 여부 확인")
	void validationUserIdExist() {
		// given
		String userId = "testId";
		when(memberRepository.userIdExistValidation(userId)).thenReturn(true);

		// when
		boolean result = memberService.validationUserIdExist(userId);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("사용자 이름 존재 여부 확인")
	void isUsernameExist() {
		// given
		String username = "testName";
		when(memberRepository.usernameExistValidation(username)).thenReturn(true);

		// when
		boolean result = memberService.isUsernameExist(username);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("사용자 ID 존재 여부 검증 - 중복 ID")
	void validateUserIdExist_DuplicateId() {
		// given
		String userId = "duplicateId";
		when(memberRepository.userIdExistValidation(userId)).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> memberService.validateUserIdExist(userId))
				.isInstanceOf(CustomDuplicatedUserIdException.class)
				.hasFieldOrPropertyWithValue("responseCode", ResponseCode.DUPLICATE_USER_ID);
	}
}
