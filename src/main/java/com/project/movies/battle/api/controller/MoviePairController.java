package com.project.movies.battle.api.controller;

import com.project.movies.battle.api.service.MoviePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviePairController {
    @Autowired
    MoviePairService moviePairService;

    @RequestMapping(value = "api/v1/movies")
    public Pair<String, String> getMoviesPair() {
        return moviePairService.getMoviesPairTitles();
    }

    @RequestMapping(value = "api/v1/movies/{option}")
    public String guessWhichMovieHasMajorImdbRate(@PathVariable Integer option) {
        return moviePairService.guessWhichMovieHasMajorImdbRate(option);
    }
}
