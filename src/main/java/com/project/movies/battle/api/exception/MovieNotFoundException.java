package com.project.movies.battle.api.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException() {
        super();
    }

    public MovieNotFoundException(String message) {
        super(message, new RuntimeException());
    }
}
