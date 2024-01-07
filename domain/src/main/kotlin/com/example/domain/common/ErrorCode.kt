package com.example.domain.common

enum class ErrorCode(
    val message: String,
) {
    INVALID_INPUT_VALUE_ERROR("입력 값이 유효하지 않습니다."),
    UNAUTHORIZED_ERROR("허가되지 않았습니다."),
    AUTHORIZATION_EXPIRED_ERROR("토큰이 만료 되었습니다."),
    ;
}