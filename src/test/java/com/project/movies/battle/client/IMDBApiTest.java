package com.project.movies.battle.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IMDBApiTest {

    @Autowired
    IMDBApi imdbApi;

    @Test
    void search() {
        ResultDTO resultSearch = this.imdbApi.getMostPopularMovies();
        assertNotNull(resultSearch.getResultList());
        resultSearch.getResultList().forEach(System.out::println);
    }
}
