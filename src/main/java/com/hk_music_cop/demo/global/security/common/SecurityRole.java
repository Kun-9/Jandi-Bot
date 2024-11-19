package com.hk_music_cop.demo.global.security.common;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Builder @Getter
public class SecurityRole implements GrantedAuthority {
	String authority;
//	LocalDateTime createdDate;
}
