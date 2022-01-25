package com.project.movies.battle.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Match {
    @Id
    private String id;
    @ManyToOne
    private Player player;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MoviePair> moviesAlreadyTaken = new ArrayList<>();
    @Setter
    private Boolean active;
}
