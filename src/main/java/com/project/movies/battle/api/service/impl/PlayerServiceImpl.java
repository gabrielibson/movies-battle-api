package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.entity.Player;
import com.project.movies.battle.api.repository.PlayerRepository;
import com.project.movies.battle.api.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {

    PlayerRepository playerRepository;

    PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(String username) {
        var player = Player.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .currentlyPlaying(false)
                .points(0)
                .build();

        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        return this.playerRepository.findByUsername(username);
    }
}
