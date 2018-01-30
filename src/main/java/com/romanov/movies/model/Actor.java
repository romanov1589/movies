package com.romanov.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import java.util.Set;

@Entity
public class Actor {
    private int actorid;
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;
    private Set<Movie> movies;


    public Actor(){}
    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getActorid() {
        return actorid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setActorid(int id) {
        this.actorid = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany(mappedBy = "actors")
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

}
