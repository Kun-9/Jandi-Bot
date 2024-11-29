package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.member.dto.request.JoinRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;

public interface MemberService {

	Long join(JoinRequest joinRequest);

	MemberResponse jandiLogin(JoinRequest joinRequest);

	MemberResponse findByMemberId(Long memberId);

	MemberResponse findByUserId(String userId);


	boolean validationUserIdExist(String userId);

	void validateUserIdExist(String userId);

	boolean isUsernameExist(String username);

	MemberResponse findMemberWithRoleByUserId(String userId);
}
