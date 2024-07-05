package com.twelve.challengeapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String introduce;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<UserPasswordRecord> passwordRecords = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	//회원 정보 수정
	public void editInfo(String nickname, String introduce) {
		this.nickname = nickname;
		this.introduce = introduce;
	}
	// 비밀 번호 변경
	public void ChangePassword(String password) {
		this.password = password;
	}
	//회원 탈퇴
	public void updateRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		return this.id.equals(user.getId());
	}

	// Post 관련
	public void addPost(Post post) {
		posts.add(post);
		post.setUser(this);
	}

	public void removePost(Post post) {
		posts.remove(post);
		post.setUser(null);
	}

	public void addPasswordRecord(UserPasswordRecord record) {
		this.passwordRecords.add(record);
		record.setUser(this);
	}

	public void removePasswordRecord(UserPasswordRecord record) {
		this.passwordRecords.remove(record);
		record.setUser(null);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
	}

	public void removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setUser(this);
	}

}
