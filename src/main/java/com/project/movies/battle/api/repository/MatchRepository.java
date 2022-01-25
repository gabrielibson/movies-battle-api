package com.project.movies.battle.api.repository;

import com.project.movies.battle.api.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, String> {
    @Query("from Match m where m.player.id=:player_id and active = true")
    Match findByPlayerId(@Param("player_id") String playerId);
}
