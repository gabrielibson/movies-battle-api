package com.project.movies.battle.api.repository.impl;

import com.project.movies.battle.api.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findByUsername(String username);
}
