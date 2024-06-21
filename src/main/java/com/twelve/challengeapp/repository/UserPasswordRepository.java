package com.twelve.challengeapp.repository;

import com.twelve.challengeapp.entity.UserPasswordRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPasswordRepository extends JpaRepository<UserPasswordRecord, Long> {

	//최근 비밀번호 3개 가져오기
	List<UserPasswordRecord> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);

	//가장 오래된 비밀번호 기록 삭제
	UserPasswordRecord findByUserIdAndCreatedAt(Long userId, LocalDateTime createdAt);
}
