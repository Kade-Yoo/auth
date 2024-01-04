package com.example.application.config.exception

import com.example.domain.common.ErrorCode
import com.example.domain.common.InvalidInputException
import com.example.domain.common.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {
    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedException(e: UnauthorizedException) : ErrorResponse =
        ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_ERROR.message)

    @ExceptionHandler(InvalidInputException::class)
    fun unauthorizedException(e: InvalidInputException) : ErrorResponse =
        ErrorResponse.create(e, HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT_VALUE_ERROR.message)

    @ExceptionHandler(RuntimeException::class)
    fun unauthorizedException(e: RuntimeException) : ErrorResponse =
        ErrorResponse.create(e, HttpStatus.NOT_FOUND, ErrorCode.INVALID_INPUT_VALUE_ERROR.message)
}