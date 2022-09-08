package com.msg.gauth.domain.auth.presentation;

import com.msg.gauth.domain.auth.presentation.dto.reqeust.SigninRequestDto;
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto;
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto;
import com.msg.gauth.domain.auth.services.LogoutService;
import com.msg.gauth.domain.auth.services.RefreshService;
import com.msg.gauth.domain.auth.services.SigninService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final RefreshService refreshService;
    private final LogoutService logoutService;

    private final SigninService signinService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@RequestHeader String refreshToken){
        return ResponseEntity.ok(refreshService.execute(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        logoutService.execute();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signin(@RequestBody @Valid SigninRequestDto signinRequestDto){
        return ResponseEntity.ok(signinService.execute(signinRequestDto));
    }
}
