package com.project.movies.battle.api.service;

import com.project.movies.battle.api.entity.Movie;
import org.springframework.data.util.Pair;

public interface BattleService {

    Pair<Movie, Movie> getMoviesPair();
}
