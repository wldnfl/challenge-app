package com.twelve.challengeapp.config.entity;

import org.junit.jupiter.api.Test;

import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	@Test
	void testUserBuilder(){
		User user = User.builder()
			.id(1L)
			.username("testuser")
			.password("password")
			.nickname("Test User")
			.introduce("Hello, I'm a test user")
			.email("test@example.com")
			.role(UserRole.USER)
			.build();

		assertEquals(1L, user.getId());
		assertEquals("testuser", user.getUsername());
		assertEquals("password", user.getPassword());
		assertEquals("Test User", user.getNickname());
		assertEquals("Hello, I'm a test user", user.getIntroduce());
		assertEquals("test@example.com", user.getEmail());
		assertEquals(UserRole.USER, user.getRole());
	}

	@Test
	void testEditInfo() {
		User user = User.builder()
			.nickname("Old Nickname")
			.introduce("Old Introduction")
			.build();

		user.editInfo("New Nickname", "New Introduction");

		assertEquals("New Nickname", user.getNickname());
		assertEquals("New Introduction", user.getIntroduce());
	}

	@Test
	void testWithdraw() {
		User user = User.builder()
			.role(UserRole.USER)
			.build();

		user.withdraw(UserRole.WITHDRAWAL);

		assertEquals(UserRole.WITHDRAWAL, user.getRole());
	}

	@Test
	void testEquals() {
		User user1 = User.builder().id(1L).build();
		User user2 = User.builder().id(1L).build();
		User user3 = User.builder().id(2L).build();

		assertEquals(user1, user2);
		assertNotEquals(user1, user3);
	}

	@Test
	void testAddAndRemovePost() {
		User user = User.builder().id(1L).build();
		Post post = new Post();

		user.addPost(post);
		assertTrue(user.getPosts().contains(post));
		assertEquals(user, post.getUser());

		user.removePost(post);
		assertFalse(user.getPosts().contains(post));
		assertNull(post.getUser());
	}

}
