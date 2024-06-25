package com.twelve.challengeapp.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private static final Long COMMENT_ID = 1L;
    private static final String COMMENT = "댓글 테스트";

    private Comment comment;


    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .id(COMMENT_ID)
                .content(COMMENT)
                .build();
    }

    @Test
    void testUserBuilder() {
        assertEquals(COMMENT_ID, comment.getId());
        assertEquals(COMMENT, comment.getContent());
    }

    @Test
    void testUpdate() {
        String newContent = "New Content";
        comment.update(newContent);

        assertEquals(newContent, comment.getContent());
    }

}
