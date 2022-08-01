package com.msg.gauth.domain.auth.services;

import com.msg.gauth.domain.auth.RefreshToken;
import com.msg.gauth.domain.auth.exception.ExpiredRefreshTokenException;
import com.msg.gauth.domain.auth.exception.InvalidRefreshTokenException;
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto;
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository;
import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshResponseDto execute(String refreshToken) {
        String email = jwtTokenProvider.exactEmailFromRefreshToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        RefreshToken redisRefreshToken = refreshTokenRepository.findById(user.getId())
                .orElseThrow(ExpiredRefreshTokenException::new);
        if (Objects.equals(redisRefreshToken.getToken(), refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        String access = jwtTokenProvider.generateAccessToken(email);
        String refresh = jwtTokenProvider.generateRefreshToken(email);
        ZonedDateTime expiresAt = jwtTokenProvider.getAccessExpiredTime();

        redisRefreshToken.updateToken(refresh, jwtTokenProvider.getRefreshTimeToLive());
        refreshTokenRepository.save(redisRefreshToken);

        return new RefreshResponseDto(access, refresh, expiresAt);
    }
}
