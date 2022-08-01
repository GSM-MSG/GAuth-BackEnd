package com.msg.gauth.domain.auth.services;

import com.msg.gauth.domain.auth.RefreshToken;
import com.msg.gauth.domain.auth.exception.PasswordMismatchException;
import com.msg.gauth.domain.auth.presentation.dto.reqeust.SigninRequestDto;
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto;
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository;
import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class SigninService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SigninResponseDto execute(SigninRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        String access = jwtTokenProvider.generateAccessToken(dto.getEmail());
        String refresh = jwtTokenProvider.generateRefreshToken(dto.getEmail());
        ZonedDateTime expiresAt = jwtTokenProvider.getAccessExpiredTime();

        refreshTokenRepository.save(new RefreshToken(user.getId(), refresh, jwtTokenProvider.getRefreshTimeToLive()));

        return new SigninResponseDto(access, refresh, expiresAt);
    }
}
