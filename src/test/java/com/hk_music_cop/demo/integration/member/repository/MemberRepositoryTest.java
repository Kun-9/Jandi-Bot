package com.hk_music_cop.demo.integration.member.repository;

import com.hk_music_cop.demo.global.security.common.Role;
import com.hk_music_cop.demo.member.dto.request.JoinReqeust;
import com.hk_music_cop.demo.member.dto.response.MemberResponse;
import com.hk_music_cop.demo.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	// 테스트 전역 변수
	List<JoinReqeust> testData;
	List<Long> seqValues;

	@BeforeEach
	void setup() {
		testData = Arrays.asList(
				new JoinReqeust("Test1", "testData1", "1234"),
				new JoinReqeust("Test2", "testData2", "1234"),
				new JoinReqeust("Test3", "testData3", "1234")
		);

		seqValues = new ArrayList<>();

		testData.forEach(m -> seqValues.add(memberRepository.join(m)));
	}

	@DisplayName("회원 생성")
	@Test
	void join() {
		// given
		JoinReqeust joinReqeust = new JoinReqeust("신동근", "userId1", "pass1");

		// when
		Long result = memberRepository.join(joinReqeust);

		// then
		Assertions.assertThat(result).isNotEqualTo(null);
	}


	@Test
	@DisplayName("이름 중복 가입 불가")
	void joinDup() {
		// given
		JoinReqeust joinReqeust1 = new JoinReqeust("신동근", "userId1", "pass1");
		JoinReqeust joinReqeust2 = new JoinReqeust("신동근", "userId2", "pass1");
		JoinReqeust joinReqeust3 = new JoinReqeust("임시1", "userId1", "pass1");

		// when
		memberRepository.join(joinReqeust1);

		// then
		assertThrows(DuplicateKeyException.class, () -> {
			memberRepository.join(joinReqeust2);
			memberRepository.join(joinReqeust3);
		});
	}


	@Test
	@DisplayName("고유Id로 멤버 객체 찾기")
	void findByMemberId() {
		// given
		JoinReqeust targetMember = testData.get(0);


		// when
		MemberResponse findMember = memberRepository.findByMemberId(seqValues.get(0));

		// then
		assertThat(findMember)
				.satisfies(member -> {
					assertThat(member.getName()).isEqualTo(targetMember.getName());
					assertThat(member.getUserId()).isEqualTo(targetMember.getUserId());
					assertThat(member.getPassword()).isEqualTo(targetMember.getPassword());
				});
	}


	@DisplayName("userId로 사용자 조회")
	@Test
	void findByUserId() {
		// given
		JoinReqeust targetMember = testData.get(0);

		// when
		MemberResponse findMember = memberRepository.findByUserId(targetMember.getUserId());

		// then
		assertThat(findMember)
				.satisfies(member -> {
					assertThat(member.getName()).isEqualTo(targetMember.getName());
					assertThat(member.getUserId()).isEqualTo(targetMember.getUserId());
					assertThat(member.getPassword()).isEqualTo(targetMember.getPassword());
				});

	}

	@DisplayName("모든 사용자 조회")
	@Test
	void findAll() {
		// given
		List<Tuple> exceptedValues = testData.stream()
				.map(data -> tuple(data.getName(), data.getUserId(), data.getPassword()))
				.toList();


		// when
		List<MemberResponse> findData = memberRepository.findAll();

		// then

		assertThat(findData)
				.extracting("name", "userId", "password")
				.contains(
						exceptedValues.toArray(new Tuple[0])
				);

	}

	@DisplayName("join + findById 통합")
	@Test
	void joinAndFind() {
		// given
		JoinReqeust joinReqeust = new JoinReqeust("신동근", "userId1", "pass1");

		// when
		memberRepository.join(joinReqeust);

		// then
		MemberResponse findMember = memberRepository.findByUserId("userId1");

		assertThat(findMember)
				.satisfies(member -> {
					assertThat(member.getUserId()).isEqualTo(joinReqeust.getUserId());
					assertThat(member.getPassword()).isEqualTo(joinReqeust.getPassword());
				});
	}


	@DisplayName("사용자 역할 조회")
	@Test
	void findRoleByMemberId() {

		// given
		List<String> roleByMemberId = memberRepository.findRolesByMemberId(5L);
		List<String> expectedRole = List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name());


		// when
		MemberResponse member = memberRepository.findByUserId("id1").withRoles(roleByMemberId);


		// then
		assertThat(member.getRoles())
				.containsExactlyInAnyOrder(expectedRole.toArray(String[]::new));

	}


}