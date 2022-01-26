package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.repository.MovieRepository;
import com.project.movies.battle.api.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {
    @InjectMocks
    MovieServiceImpl movieService;
    @Mock
    MovieRepository movieRepository;

    @Test
    @SuppressWarnings ({"unchecked", "varargs"})
    void shouldNotReturnSameMoviesPair() {
        Movie movie1 = new Movie("id1", "title1", "2010", 5.0);
        Movie movie2 = new Movie("id2", "title2", "2010", 6.0);
        var moviesPair = Arrays.asList(movie1, movie2);

        // simulate case [B, A]
        var moviesPair2 = Arrays.asList(movie2, movie1);

        Movie movie3 = new Movie("id3", "title3", "2010", 7.0);
        Movie movie4 = new Movie("id4", "title4", "2010", 8.0);
        var moviesPair3 = Arrays.asList(movie3, movie4);

        when(movieRepository.findTwoRandomMovies()).thenReturn(moviesPair, moviesPair2, moviesPair3);

        // first call
        movieService.getMoviesPair();

        var moviesAlreadyTaken = movieService.getMoviesAlreadyTaken();

        assertFalse(moviesAlreadyTaken.isEmpty());
        assertEquals(2, moviesAlreadyTaken.size());

        // second call
        movieService.getMoviesPair();

        // should call repository exactly 3 times
        verify(movieRepository, times(3)).findTwoRandomMovies();
    }
}
