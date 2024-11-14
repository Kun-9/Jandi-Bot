package com.hk_music_cop.demo.member.application;

import com.hk_music_cop.demo.member.dto.response.Member;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.dto.response.MemberSecurity;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		MemberResponse validMember = memberRepository.findByUserId(userId);


		if (validMember == null) {
			log.error("Member 찾기 실패 : {}", userId);
			throw new UsernameNotFoundException("로그인 아이디 찾을 수 없음");
		}

		List<String> rolesByMemberId = memberRepository.findRolesByMemberId(validMember.getMemberId());

		MemberResponse member = validMember.withRoles(rolesByMemberId);

		MemberSecurity memberSecurity = MemberSecurity.from(member);

		return new User(
				memberSecurity.getUserId(),
				memberSecurity.getPassword(),
				memberSecurity.getRoles()
		);
	}
}
