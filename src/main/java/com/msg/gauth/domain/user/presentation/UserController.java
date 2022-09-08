package com.msg.gauth.domain.user.presentation;

import com.msg.gauth.domain.user.presentation.dto.request.SignUpDto;
import com.msg.gauth.domain.user.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUpMember(@RequestBody @Valid SignUpDto signUpDto){
        signUpService.execute(signUpDto);
        return ResponseEntity.ok().build();
    }
}
