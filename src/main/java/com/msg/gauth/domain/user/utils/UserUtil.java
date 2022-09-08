package com.msg.gauth.domain.user.utils;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.UserNotFoundException;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User fetchCurrentUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            email = ((AuthDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return fetchUserByEmail(email);
    }

    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
