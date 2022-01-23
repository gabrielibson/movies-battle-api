package com.project.movies.battle.api.repository;

import com.project.movies.battle.api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String>, MovieRepositoryCustom {
}
