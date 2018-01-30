package com.romanov.movies.model;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    private int id;
    @NotNull
    @Size(min = 1, message = "Title must not be empty")
    private String title;
    @NotNull
    @Size(min = 1, message = "Description must not be empty")
    private String description;
    @NotNull
    @Size(min = 1)
    @Pattern(regexp = "^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$", message = "Not a valid time")
    private String runtime;
    @NotNull
    @Size(min = 1)
    @Pattern(regexp = "((?:19|20)\\d\\d)/(0?[1-9]|1[012])/([12][0-9]|3[01]|0?[1-9])", message = "Not a valid date")
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
