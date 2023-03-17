package com.example.webfluxtest;

import com.example.webfluxtest.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class Lec01GetSingleResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        Response response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);


    }

}
