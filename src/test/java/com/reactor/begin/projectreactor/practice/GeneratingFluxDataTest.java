package com.reactor.begin.projectreactor.practice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GeneratingFluxDataTest {
    @Test
    void createAFlux_range() {
        Flux<Integer> integerFlux = Flux.range(1, 5);

        StepVerifier.create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    void createAFlux_interval() {
        Flux<Long> take = Flux.interval(Duration.ofMillis(2))
                .take(5);

        StepVerifier.create(take)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
        .verifyComplete();


    }

}