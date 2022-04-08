package com.project.movies.battle.api.controller;

import com.project.movies.battle.api.dto.ErrorResponseDTO;
import com.project.movies.battle.api.exception.MovieNotFoundException;
import com.project.movies.battle.api.exception.OperationNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MatchControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MatchControllerExceptionHandler.class);

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO handleOperationNotPermittedException(OperationNotPermittedException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDTO handleMovieNotFoundException(MovieNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    private ErrorResponseDTO buildErrorResponse(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(status.value())
                .message(e.getMessage())
                .build();
        logger.error(errorResponseDTO.getMessage(), errorResponseDTO);
        return errorResponseDTO;
    }
}
