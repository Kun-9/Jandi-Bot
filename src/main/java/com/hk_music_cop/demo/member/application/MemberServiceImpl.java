package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.global.common.error.exceptions.*;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.member.dto.request.JoinReqeust;
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
	public Long join(JoinReqeust joinReqeust) {
		String userId = joinReqeust.getUserId();
		String username = joinReqeust.getName();

		// 이미 아이디가 존재할 때
		if (validationUserIdExist(userId)) {
			throw new CustomDuplicatedUserIdException(userId);
		}

		// 이미 유저네임이 존재할때
		if (isUsernameExist(username)) {
			throw new CustomDuplicatedNameException(username);
		}

		return memberRepository.join(new JoinReqeust(
				joinReqeust.getName(),
				joinReqeust.getUserId(),
				passwordEncoder.encode(joinReqeust.getPassword())
		));
	}

	@Override
	public MemberResponse jandiLogin(JoinReqeust joinReqeust) {
		String userId = joinReqeust.getUserId();

		// 존재하지 않으면
		if (!validationUserIdExist(userId)) {
			throw new CustomUnknownMemberException(userId);
		}

		MemberResponse findMember = findByUserId(userId);

		// 비밀번호가 같지 않으면
		if (!findMember.getPassword().equals(joinReqeust.getPassword())) {
			throw new CustomException(ResponseCode.LOGIN_FAIL);
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
