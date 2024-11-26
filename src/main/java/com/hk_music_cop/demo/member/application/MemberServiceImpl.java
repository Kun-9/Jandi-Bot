package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.global.common.error.exceptions.*;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.member.dto.request.JoinRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Long join(JoinRequest joinRequest) {
		String userId = joinRequest.getUserId();
		String username = joinRequest.getName();

		// 이미 아이디가 존재할 때
		if (validationUserIdExist(userId)) {
			throw new CustomDuplicatedUserIdException(userId);
		}

		// 이미 유저네임이 존재할때
		if (isUsernameExist(username)) {
			throw new CustomDuplicatedNameException(username);
		}

		return memberRepository.join(new JoinRequest(
				joinRequest.getName(),
				joinRequest.getUserId(),
				passwordEncoder.encode(joinRequest.getPassword())
		));
	}

	@Override
	public MemberResponse jandiLogin(JoinRequest joinRequest) {
		String userId = joinRequest.getUserId();

		// 존재하지 않으면
		if (!validationUserIdExist(userId)) {
			throw new CustomUnknownMemberException(userId);
		}

		MemberResponse findMember = findByUserId(userId);

		// 비밀번호가 같지 않으면
		if (!findMember.getPassword().equals(joinRequest.getPassword())) {
			throw new CustomException(ErrorCode.LOGIN_FAIL);
		}

		return findMember;
	}

	@Override
	public MemberResponse findByMemberId(Long memberId) {
		return memberRepository.findByMemberId(memberId);
	}

	@Override
	public MemberResponse findByUserId(String userId) {
		return memberRepository.findByUserId(userId);
	}

	@Override
	public boolean validationUserIdExist(String userId) {
		return memberRepository.userIdExistValidation(userId);
	}

	@Override
	public boolean isUsernameExist(String username) {
		return memberRepository.usernameExistValidation(username);
	}

	@Override
	public void validateUserIdExist(String userId) {
		if (memberRepository.userIdExistValidation(userId)) {
			throw new CustomDuplicatedUserIdException(userId);
		}
	}
}
