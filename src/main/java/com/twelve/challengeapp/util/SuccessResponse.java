package com.twelve.challengeapp.util;

public record SuccessResponse<T>(int status, String message, T data) {
}
