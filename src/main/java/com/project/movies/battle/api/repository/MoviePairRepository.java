package com.project.movies.battle.api.repository;

import com.project.movies.battle.api.entity.MoviePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MoviePairRepository extends JpaRepository<MoviePair, String> {
    @Query("from MoviePair mp where mp.match.id=:match_id and active = true")
    Optional<MoviePair> findByMatchId(@Param("match_id") String matchId);
}
