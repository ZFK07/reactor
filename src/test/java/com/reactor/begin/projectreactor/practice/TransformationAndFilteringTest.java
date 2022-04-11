package com.reactor.begin.projectreactor.practice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TransformationAndFilteringTest {
    @Test
    void testSkip() {
        Flux<String> skipFlux = Flux.just("One", "Two", "Three", "four")
                .skip(3);
        StepVerifier.create(skipFlux)
                .expectNext("four")
                .verifyComplete();
    }

    @Test
    void testSkipWithDuration() {
        Flux<String> skipFlux = Flux.just("One", "Two", "Three", "four")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));
        StepVerifier.create(skipFlux)
                .expectNext("One")
                .expectNext("Two")
                .expectNext("Three")
                .verifyComplete();
    }

    @Test
    void testFilter() {
        Flux<String> animals = Flux.just("Tiger", "Lion", "Cheetah", "Fish")
                .filter(animal -> !animal.equals("Fish"));

        StepVerifier.create(animals)
                .expectNext("Tiger", "Lion", "Cheetah")
                .verifyComplete();
    }


    @Test
    void testDistinct() {
        Flux<String> names = Flux.just("Tiger", "Fish", "Fish").distinct();
        StepVerifier.create(names)
                .expectNext("Tiger", "Fish")
                .verifyComplete();

    }

}