package com.msg.gauth.domain.email.repository;

import com.msg.gauth.domain.email.EmailAuthEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuthEntity, String> {
}
