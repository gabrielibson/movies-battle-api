package com.project.movies.battle.api.service.impl;

import com.project.movies.battle.api.entity.Match;
import com.project.movies.battle.api.entity.Movie;
import com.project.movies.battle.api.entity.MoviePair;
import com.project.movies.battle.api.exception.MovieNotFoundException;
import com.project.movies.battle.api.exception.OperationNotPermittedException;
import com.project.movies.battle.api.repository.MoviePairRepository;
import com.project.movies.battle.api.service.MatchService;
import com.project.movies.battle.api.service.MoviePairService;
import com.project.movies.battle.api.service.MovieService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.movies.battle.api.exception.ExceptionMessages.MOVIE_NOT_FOUND_FOR_THE_CURRENT_MATCH;
import static com.project.movies.battle.api.exception.ExceptionMessages.SHOULD_NOT_GO_AHEAD_WITHOUT_GUESSING;

@Service
public class MoviePairServiceImpl implements MoviePairService {
    MatchService matchService;
    MovieService movieService;
    MoviePairRepository moviePairRepository;

    public MoviePairServiceImpl(
            MatchService matchService, MovieService movieService, MoviePairRepository moviePairRepository) {
        this.matchService = matchService;
        this.movieService = movieService;
        this.moviePairRepository = moviePairRepository;
    }

    @Override
    @Transactional
    public Pair<String, String> getMoviesPairTitles() {
        var match = matchService.getCurrentMatch();
        if(!match.getMoviesAlreadyTaken().isEmpty()) {
            var moviePair = moviePairRepository.findByMatchId(match.getId());
            if(moviePair.isPresent()) {
                throw new OperationNotPermittedException(SHOULD_NOT_GO_AHEAD_WITHOUT_GUESSING);
            }
        }
        var moviesPair = movieService.getMoviesPair();
        updateMatchWithMoviePairTaken(match, moviesPair);
        return Pair.of(moviesPair.get(0).getTitle(), moviesPair.get(1).getTitle());
    }

    @Override
    public String guessWhichMovieHasMajorImdbRate(Integer option) {
        var match = matchService.getCurrentMatch();

        var moviePair = moviePairRepository.findByMatchId(match.getId())
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND_FOR_THE_CURRENT_MATCH));
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
        match.getPlayer().setPoints(++points);
        matchService.updateMatch(match);
    }

    private void updateMatchWithMoviePairTaken(Match match, List<Movie> moviesPair) {
        var moviePairTaken = MoviePair.builder()
                .movieOne(moviesPair.get(0))
                .movieTwo(moviesPair.get(1))
                .match(match)
                .active(true)
                .build();
        match.getMoviesAlreadyTaken().add(moviePairTaken);
        this.matchService.updateMatch(match);
    }
}
