package com.twelve.challengeapp.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommentRequestDtoTest {

    @Test
    void testCommentRequestDto() {
        // given
        String content = "Test comment";

        // when
        CommentRequestDto dto = new CommentRequestDto(content);

        // then
        assertEquals(content, dto.getContent());
    }
}
