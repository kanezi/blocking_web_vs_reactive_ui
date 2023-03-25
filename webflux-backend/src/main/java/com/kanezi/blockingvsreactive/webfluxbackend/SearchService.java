package com.kanezi.blockingvsreactive.webfluxbackend;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Log4j2
public class SearchService {

    Flux<SearchResult> search(String query) {
        log.info("search start: {}", query);
        return Flux.range(1, query.length())
                .map(i -> new SearchResult(String.format("%d: %s", i, query)))
                .delayElements(Duration.ofMillis(500))
                .log();
    }
}
