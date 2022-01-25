package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.entity.MoviePair;
import com.project.movies.battle.api.exception.OperationNotPermittedException;
import com.project.movies.battle.api.repository.MoviePairRepository;
import com.project.movies.battle.api.service.MatchService;
import com.project.movies.battle.api.service.MoviePairService;
import com.project.movies.battle.api.service.MovieService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class MoviePairServiceImpl implements MoviePairService {
    MatchService matchService;
    MovieService movieService;
    MoviePairRepository moviePairRepository;

    public MoviePairServiceImpl(MatchService matchService, MovieService movieService, MoviePairRepository moviePairRepository) {
        this.matchService = matchService;
        this.movieService = movieService;
        this.moviePairRepository = moviePairRepository;
    }

    @Override
    public Pair<String, String> getMoviesPairTitles() {
        var match = matchService.getCurrentMatch();
        if(!match.getMoviesAlreadyTaken().isEmpty()) {
            var moviePair = moviePairRepository.findByMatchId(match.getId());
            if(moviePair.isPresent()) {
                throw new OperationNotPermittedException("You can't go ahead without guessing which movie has the highest imdb rate");
            }
        }
        var moviesPair = movieService.getMoviesPair();
        var moviePairTaken = MoviePair.builder()
                .movieOne(moviesPair.get(0))
                .movieTwo(moviesPair.get(1))
                .active(true)
                .build();
        match.getMoviesAlreadyTaken().add(moviePairTaken);
        var matchSaved = this.matchService.updateMatch(match);
        moviePairTaken.setMatch(matchSaved);
        this.moviePairRepository.save(moviePairTaken);
        return Pair.of(moviesPair.get(0).getTitle(), moviesPair.get(1).getTitle());
    }

    @Override
    public String guessWhichMovieHasMajorImdbRate(Integer option) {
        var match = matchService.getCurrentMatch();

        var moviePair = moviePairRepository.findByMatchId(match.getId()).orElseThrow();
        var movieRate1 = moviePair.getMovieOne().getImdbRating();
        var movieRate2 = moviePair.getMovieTwo().getImdbRating();

        moviePair.setActive(false);
        moviePairRepository.save(moviePair);

        if(option == 1) {
            if(movieRate1 > movieRate2) {
                this.addPoints();
                return "right answer";
            }else {
                return "wrong answer";
            }
        }else {
            if(movieRate2 > movieRate1) {
                this.addPoints();
                return "right answer";
            }else {
                return "wrong answer";
            }
        }
    }

    private void addPoints() {
        var match = matchService.getCurrentMatch();
        var points = match.getPlayer().getPoints();
        points = ++points;
        match.getPlayer().setPoints(points);
        matchService.updateMatch(match);
    }
}
