package com.example.domain.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.example.domain"])
@EnableJpaRepositories(basePackages = ["com.example.domain"])
class JpaConfig