package com.example.restplay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono


@SpringBootApplication
@EnableWebFluxSecurity
class RestplayApplication {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        val principalExtractor = SubjectDnX509PrincipalExtractor()

        principalExtractor.setSubjectDnRegex("OU=(.*?)(?:,|$)")

        val authenticationManager = ReactiveAuthenticationManager { authentication: Authentication ->
            authentication.isAuthenticated = "Trusted Org Unit" == authentication.name
            Mono.just(authentication)
        }

        http
            .x509 { x509 ->
                x509
                    .principalExtractor(principalExtractor)
                    .authenticationManager(authenticationManager)
            }
            .authorizeExchange { exchanges ->
                exchanges
                    .anyExchange().authenticated()
            }
        return http.build()

    }
}

fun main(args: Array<String>) {
    runApplication<RestplayApplication>( *args)
}
