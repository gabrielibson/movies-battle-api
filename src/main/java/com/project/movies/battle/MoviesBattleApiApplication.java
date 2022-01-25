package com.project.movies.battle;

import com.project.movies.battle.api.entity.Movie;
import com.project.movies.battle.api.repository.MovieRepository;
import com.project.movies.battle.client.IMDBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableFeignClients
public class MoviesBattleApiApplication {

	@Autowired
	IMDBApi imdbApi;
	@Autowired
	MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(MoviesBattleApiApplication.class, args);
	}

	@Bean
	void populateDB() {
		var moviesIMDBList = this.imdbApi.getMostPopularMovies();

		var moviesList = moviesIMDBList.getResultList().stream().map(movieIMDB ->
				Movie.builder()
						.id(movieIMDB.getId())
						.title(movieIMDB.getTitle())
						.year(movieIMDB.getYear().toString())
						.imdbRating(Double.parseDouble(movieIMDB.getImdbRating()))
						.build()
		).collect(Collectors.toCollection(ArrayList::new));

		movieRepository.saveAll(moviesList);
	}
}
