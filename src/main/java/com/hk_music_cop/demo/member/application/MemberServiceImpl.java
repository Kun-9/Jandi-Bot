package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.global.error.exceptions.CustomDuplicatedUserIdException;
import com.hk_music_cop.demo.global.error.exceptions.CustomIncorrectPasswordException;
import com.hk_music_cop.demo.global.error.exceptions.CustomUnknownMemberException;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
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
	public Long join(MemberRequest memberRequest) {
		String userId = memberRequest.getUserId();

		// 이미 존재할 때
		if (isUserIdExist(userId)) {
			throw new CustomDuplicatedUserIdException(userId);
		}

		return memberRepository.join(new MemberRequest(
				memberRequest.getName(),
				memberRequest.getUserId(),
				passwordEncoder.encode(memberRequest.getPassword())
		));
	}

	@Override
	public MemberResponse login(MemberRequest memberRequest) {
		String userId = memberRequest.getUserId();

		// 존재하지 않으면
		if (!isUserIdExist(userId)) {
			throw new CustomUnknownMemberException(userId);
		}

		MemberResponse findMember = findByUserId(userId);

		// 비밀번호가 같지 않으면
		if (!findMember.getPassword().equals(memberRequest.getPassword())) {
			throw new CustomIncorrectPasswordException();
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
	public boolean isUserIdExist(String userId) {
		return memberRepository.userIdExistValidation(userId);
	}

	@Override
	public void validateUserIdExist(String userId) {
		if (memberRepository.userIdExistValidation(userId)) {
			throw new CustomDuplicatedUserIdException(userId);
		}
	}
}
