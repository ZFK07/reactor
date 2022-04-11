package com.reactor.begin.projectreactor.practice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CreationFromCollectionsTest {
    @Test
    void CreateAFlux_fromArray() {
        String[] fruits = new String[]{"Apple", "Banana", "Grapes"};
        Flux<String> fruitsFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitsFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .expectNext("Grapes")
                .verifyComplete();
    }

    @Test
    void createAFlux_fromIterable() {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Grapes");
        Flux<String> fruitsFlux = Flux.fromIterable(fruits);

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .expectNext("Grapes")
                .verifyComplete();
    }

    @Test
    void createFlux_fromStream() {
        Stream<String> fruitsStream = Stream.of("Apple", "Banana", "Grapes");
        Flux<String> fruitsFlux = Flux.fromStream(fruitsStream);

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .expectNext("Grapes")
                .verifyComplete();
    }

}