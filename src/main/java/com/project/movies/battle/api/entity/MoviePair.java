package com.project.movies.battle.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MoviePair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @OneToOne
    private Movie movieOne;
    @OneToOne
    private Movie movieTwo;
    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonBackReference
    @Setter
    private Match match;
    @Setter
    private Boolean active;
}
