package com.project.movies.battle.api.service;

import com.project.movies.battle.api.entity.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlayerService {
    Player createPlayer(String username);
    Optional<Player> findByUsername(String username);
    void addPoints(Player player);
}
