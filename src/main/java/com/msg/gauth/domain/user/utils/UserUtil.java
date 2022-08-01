package com.msg.gauth.domain.user.utils;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User fetchCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return fetchUserByEmail(email);
    }

    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
