package com.msg.gauth.domain.user.repository;

import com.msg.gauth.domain.user.EmailAuthEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuthEntity, String> {
}
