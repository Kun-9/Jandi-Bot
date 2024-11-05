package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;

public interface MemberService {

	Long join(MemberRequest memberRequest);

	MemberResponse login(MemberRequest memberRequest);

	MemberResponse findByMemberId(Long memberId);

	MemberResponse findByUserId(String userId);


	boolean isUserIdExist(String userId);

	void validateUserIdExist(String userId);
}
