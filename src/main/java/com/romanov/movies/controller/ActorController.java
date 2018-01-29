package com.romanov.movies.controller;


import com.romanov.movies.model.Actor;
import com.romanov.movies.model.Movie;
import com.romanov.movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ActorController {
    @Autowired
    private ActorRepository actorRepository;

    @RequestMapping(value = "addactor")
    public String addActor(Model model){
        model.addAttribute("actor", new Actor());
        return "addActor";
    }

    @RequestMapping(value = "saveactor", method = RequestMethod.POST)
    public String save(Actor actor){
        actorRepository.save(actor);
        return "redirect:/movies";
    }
}
