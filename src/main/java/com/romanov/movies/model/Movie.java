package com.romanov.movies.model;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    private int id;
    private String title;
    private String description;
    private String runtime;
    private String release_date;
    private String director;
    private Set<Actor> actors = new HashSet<Actor>();

    public Movie(String title, String description, String runtime, String release_date, String director) {
        this.title = title;
        this.description = description;
        this.runtime = runtime;
        this.release_date = release_date;
        this.director = director;
    }
    public Movie() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "movie_actor",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "idactor")}
    )
    public Set<Actor> getActors() {
        return actors;
    }
    public void setActors(Set<Actor> actors) {
        this.actors = actors;

    }

    public boolean hasActor(Actor actor) {
        for (Actor movieActor: getActors()) {
            if (movieActor.getActorid() == actor.getActorid()) {
                return true;
            }
        }
        return false;
    }

}
