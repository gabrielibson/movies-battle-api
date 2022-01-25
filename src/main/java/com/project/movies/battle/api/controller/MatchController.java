package com.project.movies.battle.api.controller;

import com.project.movies.battle.api.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    @Autowired
    MatchService matchService;

    @RequestMapping(value = "api/v1/match/start")
    public String startMatch() {
        return matchService.startMatch();
    }

    @RequestMapping(value = "api/v1/match/end")
    public String endMatch() {
        return matchService.endMatch();
    }
}
