package com.example.restplay.routers

import com.example.restplay.handlers.LogHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RouterFunctions.route
import reactor.core.publisher.Mono


@Configuration
class LogRouter {

    @Bean
    fun functionalRoutes(handler: LogHandler): RouterFunction<ServerResponse> =
        route()
            .route(RequestPredicates.path("/")) {
                ServerResponse.ok().body(Mono.just("I am alive"))
            }
            .nest(RequestPredicates.path("/api").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))) { builder ->
                builder.GET("/fn/mono", handler::monoMessage)
                    .POST("/fn/mono", handler::monoPostMessage)
            }
            .build()
}