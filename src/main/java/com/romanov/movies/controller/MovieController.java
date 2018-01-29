package com.romanov.movies.controller;

import com.romanov.movies.model.Actor;
import com.romanov.movies.model.Movie;
import com.romanov.movies.repository.ActorRepository;
import com.romanov.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;


    @RequestMapping("/movies")
    public String index(Model model) {
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @RequestMapping(value = "add")
    public String addMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "addMovie";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Movie movie){
        movieRepository.save(movie);
        return "redirect:/movies";
    }

//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public String editRemoveEmployee(@PathVariable("id") Long studentId, Model model) {
//        repository.delete(studentId);
//        return "redirect:/students";
//    }

    @RequestMapping(value = "addMovieActor/{id}", method = RequestMethod.GET)
    public String addActor(@PathVariable("id") Integer movieId, Model model){
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("movie", movieRepository.findOne(movieId));
        return "addMovieActor";
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String editRemoveMovie(@PathVariable("id") Integer movieId, Model model) {
        movieRepository.delete(movieId);
        return "redirect:/movies";
    }

    @RequestMapping(value="/movie/{id}/actors", method=RequestMethod.GET)
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

    @RequestMapping(value = "getmovies", method = RequestMethod.GET)
    public @ResponseBody
    List<Movie> getMovies() {
        return (List<Movie>)movieRepository.findAll();
    }
}
