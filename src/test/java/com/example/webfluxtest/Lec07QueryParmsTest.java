package com.example.webfluxtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec07QueryParmsTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest() {
//        var uri = UriComponentsBuilder.fromUriString(queryString)
//                .build(10, 20);

        var map = Map.of(
                "count", 10,
                "page", 20
        );

        var integerFlux = this.webClient
                .get()
                .uri(b -> b.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
