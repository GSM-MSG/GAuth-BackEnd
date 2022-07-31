package com.msg.gauth.domain.user.presentation.dto.request;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.enums.Gender;
import com.msg.gauth.domain.user.enums.UserRole;
import com.msg.gauth.domain.user.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignUpDto {

    private String email;

    @Size(max = 60)
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
