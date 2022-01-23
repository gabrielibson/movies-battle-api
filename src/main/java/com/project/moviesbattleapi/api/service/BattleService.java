package com.project.moviesbattleapi.api.service;

import com.project.moviesbattleapi.api.entity.Movie;
import org.springframework.data.util.Pair;

public interface BattleService {

    Pair<Movie, Movie> getMoviesPair();
}
