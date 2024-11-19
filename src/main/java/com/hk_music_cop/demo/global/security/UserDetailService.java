package com.hk_music_cop.demo.global.security;

import com.hk_music_cop.demo.global.error.exceptions.CustomUsernameNotFoundException;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.dto.response.MemberSecurity;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		MemberResponse member = memberRepository.findByUserId(userId);

		if (member == null) {
			log.error("Member 찾기 실패 : {}", userId);
			throw new CustomUsernameNotFoundException(userId);
		}

		List<String> rolesByMemberId = memberRepository.findRolesByMemberId(member.getMemberId());

		System.out.println("rolesByMemberId = " + rolesByMemberId);

		MemberSecurity memberSecurity = MemberSecurity.from(
				member.withRoles(rolesByMemberId)
		);

		return CustomUser.from(memberSecurity);
	}
}
