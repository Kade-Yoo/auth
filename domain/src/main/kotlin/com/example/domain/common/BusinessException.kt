package com.example.domain.common

open class BusinessException(
    errorCode: ErrorCode,
) : RuntimeException(errorCode.message)

class UnauthorizedException(errorCode: ErrorCode) : BusinessException(errorCode)
class InvalidInputException(errorCode: ErrorCode) : BusinessException(errorCode)