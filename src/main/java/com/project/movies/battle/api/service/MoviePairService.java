package com.project.movies.battle.api.service;

import org.springframework.data.util.Pair;

public interface MoviePairService {
    Pair<String, String> getMoviesPairTitles();
    String guessWhichMovieHasMajorImdbRate(Integer option);
}
