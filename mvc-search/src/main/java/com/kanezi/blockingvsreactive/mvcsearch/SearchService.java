package com.kanezi.blockingvsreactive.mvcsearch;

import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Value
public class SearchService {

    List<SearchResult> search(String query) {
        return IntStream.rangeClosed(1, query.length())
                .mapToObj(i -> new SearchResult(String.format("%d: %s", i, query)))
                .peek(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
