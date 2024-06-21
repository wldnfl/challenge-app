package com.twelve.challengeapp.repository;

import com.twelve.challengeapp.entity.UserPasswordRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPasswordRepository extends JpaRepository<UserPasswordRecord, Long> {
	List<UserPasswordRecord> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);
}
