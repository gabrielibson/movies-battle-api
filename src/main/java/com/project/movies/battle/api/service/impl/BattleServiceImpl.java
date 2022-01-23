package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.repository.MovieRepository;
import com.project.movies.battle.api.service.BattleService;
import com.project.movies.battle.api.entity.Movie;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BattleServiceImpl implements BattleService {

    private final MovieRepository movieRepository;
    private final Set<Pair<Movie, Movie>> moviesAlreadyTaken = new HashSet<>();

    public BattleServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Pair<Movie, Movie> getMoviesPair() {
        var movies = movieRepository.findTwoRandomMovies();
        var moviesPair = Pair.of(movies.get(0), movies.get(1));

        while(moviesAlreadyTaken.contains(moviesPair)) {
            movies = movieRepository.findTwoRandomMovies();
            moviesPair = Pair.of(movies.get(0), movies.get(1));
        }

        var moviesPairReverse = Pair.of(movies.get(1), movies.get(0));

        moviesAlreadyTaken.add(moviesPair);
        moviesAlreadyTaken.add(moviesPairReverse);

        return moviesPair;
    }

    public Set<Pair<Movie, Movie>> getMoviesAlreadyTaken() {
        return moviesAlreadyTaken;
    }
}
