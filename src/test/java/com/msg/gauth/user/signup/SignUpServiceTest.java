package com.msg.gauth.user.signup;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.presentation.dto.request.SignUpDto;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.domain.user.services.SignUpService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void SignUp(){
        //given
        SignUpDto signUpDto = new SignUpDto("s21053@gsm.hs.kr", "1234");

        //when
        Long userId = signUpService.execute(signUpDto);

        //then
        Assertions.assertThat(userRepository.existsById(userId)).isTrue();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Assertions.assertThat(user.getEmail()).isEqualTo(signUpDto.getEmail());
    }
}
