package com.msg.gauth.domain.user.presentation.dto.request;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.enums.Gender;
import com.msg.gauth.domain.user.enums.UserRole;
import com.msg.gauth.domain.user.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignUpDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    public User toEntity(String password){
        return User.builder()
                .email(email)
                .password(password)
                .roles(Collections.singletonList(UserRole.ROLE_STUDENT))
                .state(UserState.PENDING)
                .build();
    }
}
