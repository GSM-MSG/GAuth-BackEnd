package com.msg.gauth.domain.auth.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SigninRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    String email;

    @NotBlank
    String password;
}
