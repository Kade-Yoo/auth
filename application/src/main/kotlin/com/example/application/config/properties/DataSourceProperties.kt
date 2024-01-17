package com.example.application.config.properties

import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean

class DataSourceProperties {

    @Value("spring.datasource.username")
    lateinit var userName: String

    @Value("spring.datasource.url")
    lateinit var url: String

    @Value("spring.datasource.driver-class-name")
    lateinit var driverClassName: String

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .username(userName)
            .url(url)
            .driverClassName(driverClassName)
            .build()
    }
}