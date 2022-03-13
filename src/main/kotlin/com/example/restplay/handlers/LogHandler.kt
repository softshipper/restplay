package com.example.restplay.handlers

import com.example.restplay.models.LogData
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.server.ResponseStatusException
import kotlin.math.log


@Slf4j
@Component
class LogHandler {

   fun monoMessage(request: ServerRequest): Mono<ServerResponse> {
        val data = mapOf("A" to "1", "B" to "2")

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                Mono.just(Json.encodeToString(data)), String::class.java
            )
    }


    fun monoPostMessage(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono<LogData>()
            .onErrorResume {
                Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "deserialize"))
            }
            .flatMap { ServerResponse.ok().build() }

    }
}