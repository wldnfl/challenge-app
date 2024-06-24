package com.twelve.challengeapp.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostRequestDtoTest {

    // 유효한 입력 테스트
    @Test
    void testPostRequestDto_ValidInput() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("Test Title")
                .content("Test Content")
                .build();

        assertNotNull(postRequestDto);
        assertEquals("Test Title", postRequestDto.getTitle());
        assertEquals("Test Content", postRequestDto.getContent());
    }

    // 제목 없는 경우 테스트
    @Test
    void testPostRequestDto_NullTitle() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .content("Test Content")
                .build();

        assertNotNull(postRequestDto);
        assertNull(postRequestDto.getTitle());
        assertEquals("Test Content", postRequestDto.getContent());
    }

    // 내용 비어 있는 경우 테스트
    @Test
    void testPostRequestDto_EmptyContent() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("Test Title")
                .content("")
                .build();

        assertNotNull(postRequestDto);
        assertEquals("Test Title", postRequestDto.getTitle());
        assertEquals("", postRequestDto.getContent());
    }
}
