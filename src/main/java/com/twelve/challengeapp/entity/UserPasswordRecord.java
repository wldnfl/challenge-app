package com.twelve.challengeapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//이력을 남긴다
//구체적으로
@Table(name = "user_password_record")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordRecord extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String userPassword;

	public UserPasswordRecord(User user, String encodedPassword) {
		this.user = user;
		this.userPassword = encodedPassword;
	}

	public UserPasswordRecord(String changePassword) {
		this.userPassword = changePassword;
	}
}
