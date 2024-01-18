package com.example.application.config.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

// TODO : 사용방법 확인 후 Custom
class CustomAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        TODO("Not yet implemented")
    }

    override fun supports(authentication: Class<*>?): Boolean {
        TODO("Not yet implemented")
    }
}