package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.config.SecurityConfiguration;
import com.project.movies.battle.api.entity.Match;
import com.project.movies.battle.api.exception.OperationNotPermittedException;
import com.project.movies.battle.api.repository.MatchRepository;
import com.project.movies.battle.api.service.MatchService;
import com.project.movies.battle.api.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MatchServiceImpl implements MatchService {

    PlayerService playerService;
    MatchRepository matchRepository;

    public MatchServiceImpl(PlayerService playerService, MatchRepository matchRepository) {
        this.playerService = playerService;
        this.matchRepository = matchRepository;
    }

    @Override
    public String startMatch() {
        String username = SecurityConfiguration.getUserLogged();
        var player= playerService.findByUsername(username).orElseGet(() -> playerService.createPlayer(username));

        if(player.isCurrentlyPlaying()) {
            throw new OperationNotPermittedException(String.format("%s has already started this match", username));
        }

        player.setCurrentlyPlaying(true);

        var match = Match.builder()
                .id(UUID.randomUUID().toString())
                .player(player)
                .active(true)
                .build();

        this.matchRepository.save(match);

        return "match has started";
    }

    @Override
    public String endMatch() {
        String username = SecurityConfiguration.getUserLogged();
        var player= playerService.findByUsername(username).orElseThrow();

        if(!player.isCurrentlyPlaying()) {
            throw new OperationNotPermittedException("You should first start a match");
        }
        var match = this.getCurrentMatch();
        player.setCurrentlyPlaying(false);
        match.setPlayer(player);
        match.setActive(false);
        matchRepository.save(match);

        return "end of match";
    }

    @Override
    public Match getCurrentMatch() {
        var username = SecurityConfiguration.getUserLogged();
        return matchRepository.findByPlayerId(this.playerService.findByUsername(username)
                .orElseThrow(() -> new OperationNotPermittedException("You should first start a match"))
                .getId());
    }

    @Override
    public Match updateMatch(Match match) {
        return this.matchRepository.save(match);
    }
}
