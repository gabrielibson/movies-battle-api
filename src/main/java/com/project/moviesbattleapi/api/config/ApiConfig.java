package com.project.moviesbattleapi.api.config;

import com.project.moviesbattleapi.api.entity.Movie;
import com.project.moviesbattleapi.api.repository.MovieRepository;
import com.project.moviesbattleapi.client.IMDBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
public class ApiConfig {

    @Autowired
    IMDBApi imdbApi;
    @Autowired
    MovieRepository movieRepository;

    @Bean
    void populateDB() {
        var moviesIMDBList = this.imdbApi.getMostPopularMovies();

        var moviesList = moviesIMDBList.getResultList().stream().map(movieIMDB ->
                Movie.builder()
                        .id(movieIMDB.getId())
                        .title(movieIMDB.getTitle())
                        .year(movieIMDB.getYear().toString())
                        .build()
        ).collect(Collectors.toCollection(ArrayList::new));

        movieRepository.saveAll(moviesList);
    }
}
