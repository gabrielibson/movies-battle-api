package com.project.movies.battle.api.exception;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException() {
        super();
    }

    public OperationNotPermittedException(String message) {
        super(message, new RuntimeException());
    }
}
