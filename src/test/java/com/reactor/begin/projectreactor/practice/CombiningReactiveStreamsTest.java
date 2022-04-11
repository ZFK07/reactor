package com.reactor.begin.projectreactor.practice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

class CombiningReactiveStreamsTest {

    @Test
    void mergeFluxes() {
        Flux<String> fruitsFlux = Flux.fromStream(Stream.of("Apple", "Banana")).delayElements(Duration.ofMillis(500));
        Flux<String> vegetableFlux = Flux.fromStream(Stream.of("Carrot", "Onion")).delaySubscription(Duration.ofMillis(1)).delayElements(Duration.ofMillis(500));

        Flux<String> stringFlux = fruitsFlux.mergeWith(vegetableFlux);

        StepVerifier.create(stringFlux)
                .expectNext("Apple")
                .expectNext("Carrot")
                .expectNext("Banana")
                .expectNext("Onion")
                .verifyComplete();
    }

    @Test
    void zipFluxes() {
        Flux<String> characterFlux = Flux.just("Kojara", "Barbossa");
        Flux<String> foodFlux = Flux.just("Apples", "Banana");
        Flux<Tuple2<String, String>> tuple2Flux = characterFlux.zipWith(foodFlux);
        StepVerifier.create(tuple2Flux)
                .expectNextMatches(p -> {
                    return p.getT1().equals("Kojara") && p.getT2().equals("Apples");
                })
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") && p.getT2().equals("Banana")
                )
                .verifyComplete();

    }

    @Test
    void zipFluxes_withCustomReturnType() {
        Flux<String> characterFlux = Flux.just("Kojara", "Barbossa");
        Flux<String> foodFlux = Flux.just("Apples", "Banana");
        Flux<HashMap<String, String>> hashMapFlux = characterFlux.zipWith(foodFlux, (x, y) -> {
            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put(x, y);
            return objectObjectHashMap;
        });
        StepVerifier.create(hashMapFlux)
                .expectNextMatches(p -> {
                    return p.get("Kojara").equals("Apples");
                })
                .expectNextMatches(p ->
                        p.get("Barbossa").equals("Banana")
                )
                .verifyComplete();
    }

    @Test
    void testFastFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "Snore").delayElements(Duration.ofSeconds(1));
        Flux<String> fastFlux = Flux.just("rabbit", "tiger");
        Flux<String> stringFlux = Flux.firstWithSignal(slowFlux, fastFlux);

        StepVerifier.create(stringFlux).expectNext("rabbit").expectNextCount(1).verifyComplete();


    }
}