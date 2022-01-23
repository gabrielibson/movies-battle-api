package com.project.movies.battle.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    @JsonProperty("items")
    private List<MovieIMDB> resultList;
}
