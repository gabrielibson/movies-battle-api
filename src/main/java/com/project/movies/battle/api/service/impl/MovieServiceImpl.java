package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.entity.Movie;
import com.project.movies.battle.api.repository.MovieRepository;
import com.project.movies.battle.api.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final Set<List<Movie>> moviesAlreadyTaken = new HashSet<>();

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMoviesPair() {
        var movies = movieRepository.findTwoRandomMovies();
        var moviesPair = Arrays.asList(movies.get(0), movies.get(1));

        while(moviesAlreadyTaken.contains(moviesPair)) {
            movies = movieRepository.findTwoRandomMovies();
            moviesPair = Arrays.asList(movies.get(0), movies.get(1));
        }

        var moviesPairReverse = Arrays.asList(movies.get(1), movies.get(0));

        moviesAlreadyTaken.add(moviesPair);
        moviesAlreadyTaken.add(moviesPairReverse);

        return moviesPair;
    }

    public Set<List<Movie>> getMoviesAlreadyTaken() {
        return moviesAlreadyTaken;
    }
}
