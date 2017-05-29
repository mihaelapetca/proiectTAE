/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proiecttae.controllers;

import com.example.proiecttae.dao.ActorRepository;
import com.example.proiecttae.dao.MovieRepository;
import com.example.proiecttae.entities.Actor;
import com.example.proiecttae.entities.Movie;
import java.util.List;
import javax.transaction.Transactional;
import static org.apache.tomcat.jni.Mmap.delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Mihaela
 */
@Controller
public class ActorController {
    
    private ActorRepository actorRepository;
    private MovieRepository movieRepository;

    @Autowired
    public ActorController(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }
    
    @GetMapping("/actors")
    public String getActors(Model model) {
        model.addAttribute("actors", actorRepository.findAll());
        return "/actors";
    }
    
    @PostMapping("/actors")
    public String saveActor(@ModelAttribute Actor actor) {
        actorRepository.save(actor);
        return "redirect:/adda";
    }
    
    @DeleteMapping("/actors/{id}")
    @Transactional
    public String deleteActor(@PathVariable("id") Long id) {
        Actor a = actorRepository.findOne(id);
        List<Movie> movies = movieRepository.findAll();
        for(Movie m: movies) {
            if(m.getActors().contains(a)) {
                m.getActors().remove(a);
            }
        }
        movieRepository.save(movies);
        actorRepository.delete(id);
        return "redirect:/actors";
    }
}
