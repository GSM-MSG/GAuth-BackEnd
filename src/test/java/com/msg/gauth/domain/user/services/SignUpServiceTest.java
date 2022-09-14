package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.presentation.dto.request.SignUpDto;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.domain.auth.services.SignUpService;
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


@SpringBootTest
public class SignUpServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserRepository userRepository;
    private static ValidatorFactory validatorFactory;
    private static Validator validatorFromFactory;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validatorFromFactory = validatorFactory.getValidator();
    }

    @AfterEach
    public void deleteTestData(){
        userRepository.deleteAll();
    }

    @Test
    public void SignUp(){
        //given
        SignUpDto signUpDto = new SignUpDto("s21053@gsm.hs.kr", "123456789999");

        //when
        Long userId = signUpService.execute(signUpDto);

        //then
        Assertions.assertThat(userRepository.existsById(userId)).isTrue();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Assertions.assertThat(user.getEmail()).isEqualTo(signUpDto.getEmail());
        org.junit.jupiter.api.Assertions.assertThrows(DuplicateEmailException.class, () -> signUpService.execute(signUpDto));
    }

    @Test
    public void PasswordValidationTest(){
        SignUpDto signUpDto = new SignUpDto("s21053@gsm.hs.kr", "123");
        Set<ConstraintViolation<SignUpDto>> validate = validatorFromFactory.validate(signUpDto);
        Assertions.assertThat(validate).isNotEmpty();
    }

    @Test
    public void EmailValidationTest(){
        SignUpDto signUpDto = new SignUpDto("s21053hs.kr", "123219081");
        Set<ConstraintViolation<SignUpDto>> validate = validatorFromFactory.validate(signUpDto);
        Assertions.assertThat(validate).isNotEmpty();
    }
    
    @Test
    public void PasswordEncodeTest(){
        SignUpDto signUpDto = new SignUpDto("s21053@gsm.hs.kr", "123456789999");
        Long id = signUpService.execute(signUpDto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
        Assertions.assertThat(user.getPassword()).isNotEqualTo(signUpDto.getPassword());
        Assertions.assertThat(passwordEncoder.matches(signUpDto.getPassword(), user.getPassword())).isTrue();
    }
}
