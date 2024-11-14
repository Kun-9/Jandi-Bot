package com.hk_music_cop.demo.member.repository;

import com.hk_music_cop.demo.global.config.security.SecurityRole;
import com.hk_music_cop.demo.member.dto.request.MemberRequest;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MemberRepository {

	/**
	 *
	 * @param member 멤버 RequestDTO
	 * @return 시퀀스 값 반환
	 */
	Long join(MemberRequest member);

	/**
	 *
	 * @param memberId 멤버 시퀀스 아이디
	 * @return 멤버 ResponseDTO
	 */
	MemberResponse findByMemberId(Long memberId);

	/**
	 *
	 * @param userId 멤버 유저 로그인 아이디
	 * @return 멤버 ResponseDTO
	 */
	MemberResponse findByUserId(String userId);

	/**
	 *
	 * @return 모든 멤버 반환
	 */
	List<MemberResponse> findAll();


	/**
	 *
	 * @param userId 존재하는지 확인 할 유저 아이디
	 * @return 존재 여부
	 */
	boolean userIdExistValidation(String userId);

	List<String> findRolesByMemberId(Long memberId);
}
