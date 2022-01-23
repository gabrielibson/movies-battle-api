package com.project.movies.battle.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "imdb", url = "${imdb.url}")
public interface IMDBApi {

    @GetMapping
    ResultDTO getMostPopularMovies();
}
