package com.kanezi.blockingvsreactive.webfluxbackend;

import lombok.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Value
@CrossOrigin
public class SearchApiController {

    SearchService searchService;

    @GetMapping("/api/search")
    Flux<SearchResult> search(@RequestParam String q) {
        return searchService.search(q);
    }
}
