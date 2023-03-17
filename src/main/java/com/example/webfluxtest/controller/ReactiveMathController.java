package com.example.webfluxtest.controller;

import com.example.webfluxtest.dto.MultiplyRequestDto;
import com.example.webfluxtest.dto.Response;
import com.example.webfluxtest.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {

        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> mutiapplicationTable(@PathVariable int input) {
//        Mono<List<Response>>
        return this.mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> mutiapplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return this.mathService.multiply(requestDtoMono);

    }
}
