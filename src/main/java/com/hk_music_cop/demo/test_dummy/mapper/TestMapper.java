package com.hk_music_cop.demo.test_dummy.mapper;

import com.hk_music_cop.demo.test_dummy.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

	List<User> findAll();

	User findById(Long id);
}
