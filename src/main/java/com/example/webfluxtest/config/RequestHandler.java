package com.example.webfluxtest.config;

import com.example.webfluxtest.dto.MultiplyRequestDto;
import com.example.webfluxtest.dto.Response;
import com.example.webfluxtest.exception.InputValidationException;
import com.example.webfluxtest.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        var responseMono = this.mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        var responseFlux = this.mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        var responseFlux = this.mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        var requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);

        var responseMono = this.mathService.multiply(requestDtoMono);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));

        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        var responseMono = this.mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
