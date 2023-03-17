package com.example.webfluxtest.service;

import com.example.webfluxtest.dto.MultiplyRequestDto;
import com.example.webfluxtest.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
                .map(i -> new Response(i * input));

//        List<Response> list = IntStream.rangeClosed(1, 10)
//                .peek(i -> SleepUtil.sleepSeconds(1))
//                .peek(i -> System.out.println("math-service processing : " + i))
//                .mapToObj(i -> new Response(i * input))
//                .collect(Collectors.toList());
//
//        return Flux.fromIterable(list);
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtomono) {
        return dtomono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }



}

