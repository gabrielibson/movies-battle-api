package com.project.movies.battle.api.repository;

import com.project.movies.battle.api.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findTwoRandomMovies();
}
