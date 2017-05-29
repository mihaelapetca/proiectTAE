/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proiecttae.controllers;

import com.example.proiecttae.dao.ActorRepository;
import com.example.proiecttae.dao.GenreRepository;
import com.example.proiecttae.dao.MovieRepository;
import com.example.proiecttae.entities.Actor;
import com.example.proiecttae.entities.Genre;
import com.example.proiecttae.entities.Movie;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Mihaela
 */
@Controller
public class MovieController {
    
    private MovieRepository movieRepository;
    private ActorRepository actorRepository;
    private GenreRepository genreRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
        //movieRepository.save(new Movie(1L, "Divergent", "HEllo", null, null, null));
    }
    
    @GetMapping("/")
    public String getMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("movie", new Movie());
        model.addAttribute("allActors", actorRepository.findAll());
        model.addAttribute("allGenres", genreRepository.findAll());
        return "index";
    }
    
//    @GetMapping("/addSelectedActor")
//    public String addToCart(@RequestParam Long id, HttpSession session){
//        Actor a = actorRepository.findOne(id);
//        List<Actor> actors = (List<Actor>) session.getAttribute("selectedActors");
//        if (actors == null){
//            actors = new ArrayList<>();
//        }
//        actors.add(a);
//        session.setAttribute("selectedActors", actors);
//        return "redirect:/";
//    }
    
    @PostMapping("/")
    @Transactional
    public String saveMovie(@ModelAttribute Movie movie) {       
        //movie.setSelectedActors((List<Actor>) session.getAttribute("selectedActors"));
        //movie.setSelectedActors(actors);
        movieRepository.save(movie);       
        return "redirect:/";
    }
    
    @GetMapping("/{id}")
    public String selectMovie(@PathVariable("id") Long id, Model model, HttpSession session) {
        session.setAttribute("current", movieRepository.findOne(id));
        model.addAttribute("actors", movieRepository.findOne(id).getActors());
        model.addAttribute("genres", movieRepository.findOne(id).getActors());
        return "redirect:/";
    }
    
    @PostMapping("/{id}/edit")
    @Transactional
    public String editMovie(@PathVariable("id") Long id, @ModelAttribute Movie movie, HttpSession session) {
        Movie m = movieRepository.findOne(id);
        m.setName(movie.getName());
        m.setDescription(movie.getDescription());
        m.setImage(movie.getImage());
        m.setActors(movie.getActors());
        m.setGenres(movie.getGenres());
        session.invalidate();
        return "redirect:/";
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public String deleteMovie(@PathVariable("id") Long id) {
        movieRepository.delete(id);
        return "redirect:/";
    }
    
    @GetMapping("/add")
    public String getToSaveMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("allActors", actorRepository.findAll());
        model.addAttribute("allGenres", genreRepository.findAll());
        return "/save";
    }
    
    @GetMapping("/addg")
    public String getGenre(Model model) {
        model.addAttribute("genre", new Genre());
        return "/addgenre";
    }
    
    @GetMapping("/adda")
    public String getToSaveActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "/addactor";
    }
}
