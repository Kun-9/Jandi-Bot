package com.hk_music_cop.demo.global.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

@Builder @Getter
public class SecurityRole implements GrantedAuthority {
	String authority;
//	LocalDateTime createdDate;
}
