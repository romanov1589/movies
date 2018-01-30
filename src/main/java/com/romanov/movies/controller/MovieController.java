package com.romanov.movies.controller;

import com.romanov.movies.model.Actor;
import com.romanov.movies.model.Movie;
import com.romanov.movies.repository.ActorRepository;
import com.romanov.movies.repository.MovieRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Api(
        name="Movie Factory API",
        description = "Provides a list of methods that manage movies",
        stage= ApiStage.RC)
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @RequestMapping("/")
    @ApiMethod(description = "Our index page")
    public String index(){
        return "redirect:/login";
    }

    @RequestMapping("/login")
    @ApiMethod(description = "Got to login page")
    public String login() {
        return "login";
    }

    @RequestMapping("/movies")
    @ApiMethod(description = "Get a list of all movies in the database")
    public String movies(Model model) {
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }
    @RequestMapping(value = "/addmovie")
    @ApiMethod(description = "Got add movie page")
    public String addMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "addMovie";
    }
    @ApiMethod(description = "Add new movie to database")
    @RequestMapping(value = "/savemovie", method = RequestMethod.POST)
    public String save(@ModelAttribute @Valid Movie movie, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "addMovie";
        }
            movieRepository.save(movie);
            return "redirect:/movies";


    }

    @RequestMapping(value = "addMovieActor/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Go to page there we can add a new actor to movie")
    public String addActor(@PathVariable("id") Integer movieId, Model model){
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("movie", movieRepository.findOne(movieId));
        return "addMovieActor";
    }
    @RequestMapping(value = "/deletemovie/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Delete movie from database by id")
    public String editRemoveMovie(@PathVariable("id") Integer movieId, Model model) {
        movieRepository.delete(movieId);
        return "redirect:/movies";
    }

    @RequestMapping(value="/movie/{id}/actors", method=RequestMethod.GET)
    @ApiMethod(description = "Add new actor to movie")
    public String moviesAddActor(@PathVariable Integer id, @RequestParam Integer actorId, Model model) {
        Actor actor = actorRepository.findOne(actorId);
        Movie movie = movieRepository.findOne(id);

        if (movie != null) {
            if (!movie.hasActor(actor)) {
                movie.getActors().add(actor);
            }
            movieRepository.save(movie);
            model.addAttribute("movie", actorRepository.findOne(id));
            model.addAttribute("actors", actorRepository.findAll());
            return "redirect:/movies";
        }

        return "redirect:/movies";
    }


}
