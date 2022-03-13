package com.example.restplay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain


@SpringBootApplication
class RestplayApplication {

    @Bean
    fun reactiveUserDetailsService(): ReactiveUserDetailsService? {
        // @formatter:off
        val client: UserDetails = User.withUsername("client")
            .password("")
            .roles("USER")
            .build()
        // @formatter:on
        return MapReactiveUserDetailsService(client)
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        // @formatter:off
        http
            .x509(withDefaults())
            .authorizeExchange { exchanges ->
                exchanges
                    .anyExchange().permitAll()
            }
        return http.build()
    }
}

fun main(args: Array<String>) {
    runApplication<RestplayApplication>( *args)
}
