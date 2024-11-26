package com.hk_music_cop.demo.member.dto.request;

import com.hk_music_cop.demo.member.message.MemberValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter @ToString
public class JoinRequest {

	@NotBlank(message = MemberValidationMessage.SignUp.NAME_BLANK)
	@Size(max = 10, message = MemberValidationMessage.SignUp.NAME_SIZE)
	@Pattern(regexp = "^\\S*$", message = MemberValidationMessage.SignUp.NAME_FORMAT)
	private String name;

	@NotBlank(message = MemberValidationMessage.SignUp.USER_ID_BLANK)
	@Size(max = 10, message = MemberValidationMessage.SignUp.USER_ID_SIZE)
	@Pattern(regexp = "^\\S*$", message = MemberValidationMessage.SignUp.USER_ID_FORMAT)
	private String userId;

	@NotBlank(message = MemberValidationMessage.SignUp.PASSWORD_BLANK)
	@Size(max = 15, message = MemberValidationMessage.SignUp.PASSWORD_SIZE)
	private String password;
}
