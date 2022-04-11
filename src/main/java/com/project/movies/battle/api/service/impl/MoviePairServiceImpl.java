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
import static com.project.movies.battle.api.exception.ExceptionMessages.CHOSEN_OPTION_NOT_PERMITTED;
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
    @Transactional
    public String guessWhichMovieHasMajorImdbRate(Integer option) {
        var match = matchService.getCurrentMatch();

        if(!List.of(1, 2).contains(option))
                throw new OperationNotPermittedException(CHOSEN_OPTION_NOT_PERMITTED);

        var moviePair = moviePairRepository.findByMatchId(match.getId())
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND_FOR_THE_CURRENT_MATCH));

        Movie chosenMovie = option == 1 ? moviePair.getMovieOne() : moviePair.getMovieTwo();
        Movie competitorMovie = option == 1 ? moviePair.getMovieTwo() : moviePair.getMovieOne();

        moviePair.setActive(false);
        moviePairRepository.save(moviePair);

        //player gain points in draw cases
        if(chosenMovie.getImdbRating() >= competitorMovie.getImdbRating()) {
            this.matchService.addPoints(match);
            return "right answer";
        }else {
            return "wrong answer";
        }
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
