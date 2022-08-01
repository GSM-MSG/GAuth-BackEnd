package com.msg.gauth.domain.auth.repository;

import com.msg.gauth.domain.auth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
