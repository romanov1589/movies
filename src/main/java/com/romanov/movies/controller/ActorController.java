package com.romanov.movies.controller;


import com.romanov.movies.model.Actor;
import com.romanov.movies.model.Movie;
import com.romanov.movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ActorController extends WebMvcConfigurerAdapter {
    @Autowired
    private ActorRepository actorRepository;

    @RequestMapping("/actors")
    public String index(Model model) {
        List<Actor> actors = (List<Actor>) actorRepository.findAll();
        model.addAttribute("actors", actors);
        return "actors";
    }

    @RequestMapping(value = "addactor")
    public String addActor(Model model){
        model.addAttribute("actor", new Actor());
        return "addActor";
    }

    @PostMapping(value = "saveactor")
    public String save(@ModelAttribute @Valid Actor actor, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "addActor";
        }
            actorRepository.save(actor);
            return "redirect:/actors";
    }

    @RequestMapping(value = "/deleteactor/{id}", method = RequestMethod.GET)
    public String removeActor(@PathVariable("id") Integer actorId, Model model) {
        try {
            actorRepository.delete(actorId);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/actors";
    }
    @RequestMapping(value = "/editactor/{id}", method = RequestMethod.GET)
    public String editActor(@PathVariable("id") Integer actorId, Model model){
        Actor actor = actorRepository.getOne(actorId);
        model.addAttribute("actor", actor);
        return "editActor";
    }
    @RequestMapping(value = "/editactor/{id}", method = RequestMethod.POST)
    public String editActor(@ModelAttribute("actor") Actor actor, @PathVariable("id") Integer actorId, Model model){
        Actor currentActor = actorRepository.getOne(actorId);
        currentActor.setFirstName(actor.getFirstName());
        currentActor.setLastName(actor.getLastName());
        actorRepository.save(currentActor);
        return "redirect:/actors";
    }
}
