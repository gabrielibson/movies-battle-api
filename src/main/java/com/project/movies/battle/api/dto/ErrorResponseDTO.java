package com.project.movies.battle.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponseDTO {
    private int status;
    private String message;
}
