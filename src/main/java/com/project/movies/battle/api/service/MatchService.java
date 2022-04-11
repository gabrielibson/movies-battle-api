package com.project.movies.battle.api.service;

import com.project.movies.battle.api.entity.Match;

public interface MatchService {
    String startMatch();
    String endMatch();
    Match getCurrentMatch();
    void updateMatch(Match match);
    void addPoints(Match match);
}
