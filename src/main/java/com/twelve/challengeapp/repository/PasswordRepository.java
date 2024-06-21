package com.twelve.challengeapp.repository;

import com.twelve.challengeapp.entity.UserPasswordRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<UserPasswordRecord, Long> {
}
