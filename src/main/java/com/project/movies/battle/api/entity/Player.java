package com.project.movies.battle.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    private String id;
    @Column(unique = true)
    private String username;
    @Setter
    private Integer points;
    private Integer totalPoints;
}
