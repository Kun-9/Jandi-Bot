package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.member.dto.request.JoinReqeust;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;

public interface MemberService {

	Long join(JoinReqeust joinReqeust);

	MemberResponse jandiLogin(JoinReqeust joinReqeust);

	MemberResponse findByMemberId(Long memberId);

	MemberResponse findByUserId(String userId);


	boolean validationUserIdExist(String userId);

	void validateUserIdExist(String userId);

	boolean isUsernameExist(String username);
}
