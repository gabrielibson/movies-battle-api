package com.project.movies.battle.api.repository.impl;

import com.project.movies.battle.api.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieRepositoryImplTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void shouldFindTwoRandomMovies() {
        var result = movieRepository.findTwoRandomMovies();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
