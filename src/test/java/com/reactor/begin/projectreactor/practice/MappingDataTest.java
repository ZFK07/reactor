package com.reactor.begin.projectreactor.practice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class MappingDataTest {
    @Test
    void mappingUsingFlux() {
        Flux<Player> players = Flux.just("Zahid Khan", "Arif Khan", "Saru mir", "Obaid Sheikh").map(fullname -> {
            String[] split = fullname.split("\\s");
            return new Player(split[0], split[1]);
        });

        StepVerifier.create(players).expectNext(new Player("Zahid", "Khan")).expectNext(new Player("Arif", "Khan")).expectNext(new Player("Saru", "mir")).expectNext(new Player("Obaid", "Sheikh")).verifyComplete();

    }

    @Test
    void mappingUsingFlatMap() {

        Flux<Player> players = Flux.just("Zahid Khan", "Arif Khan", "Saru mir", "Obaid Sheikh")
                .flatMap(fullname -> Mono.just(fullname).map(p -> {
            String[] split = p.split("\\s");
            return new Player(split[0], split[1]);
        }));
        List<Player> playerList = Arrays.asList(new Player("Zahid", "Khan"), new Player("Arif", "Khan"), new Player("Saru", "mir"), new Player("Obaid", "Sheikh"));

        StepVerifier.create(players).expectNextMatches(player -> playerList.contains(player)).expectNextMatches(player -> playerList.contains(player)).expectNextMatches(player -> playerList.contains(player)).expectNextMatches(player -> playerList.contains(player)).verifyComplete();

    }


    class Player {
        String name;
        String fname;

        public Player(String name, String fname) {
            this.name = name;
            this.fname = fname;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Player player = (Player) o;
            return Objects.equals(name, player.name) && Objects.equals(fname, player.fname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, fname);
        }
    }

}