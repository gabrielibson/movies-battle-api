package com.project.moviesbattleapi.api.repository;

import com.project.moviesbattleapi.api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String>, MovieRepositoryCustom {
}
