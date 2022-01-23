package com.project.moviesbattleapi.api.repository;

import com.project.moviesbattleapi.api.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findTwoRandomMovies();
}
