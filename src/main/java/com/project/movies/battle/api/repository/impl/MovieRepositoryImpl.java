package com.project.movies.battle.api.repository.impl;

import com.project.movies.battle.api.entity.Movie;
import com.project.movies.battle.api.repository.MovieRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Movie> findTwoRandomMovies() {
        String query = "SELECT new com.project.movies.battle.api.entity.Movie(id, title, year, imdbRating)" +
                " FROM Movie m " +
                "ORDER BY random()";

        var typedQuery = entityManager.createQuery(query, Movie.class);
        typedQuery.setMaxResults(2);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void saveAllMovies(List<Movie> movies) {
        movies.forEach(movie -> entityManager.persist(movie));
    }
}
