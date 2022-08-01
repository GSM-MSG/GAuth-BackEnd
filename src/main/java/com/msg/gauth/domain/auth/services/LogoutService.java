package com.msg.gauth.domain.auth.services;

import com.msg.gauth.domain.auth.RefreshToken;
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository;
import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final UserUtil userUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    void execute() {
        User currentUser = userUtil.fetchCurrentUser();
        refreshTokenRepository.findById(currentUser.getId())
                .ifPresent(refreshTokenRepository::delete);
    }
}
