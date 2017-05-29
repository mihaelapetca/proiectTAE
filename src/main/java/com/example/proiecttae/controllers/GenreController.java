/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proiecttae.controllers;

import com.example.proiecttae.dao.GenreRepository;
import com.example.proiecttae.dao.MovieRepository;
import com.example.proiecttae.entities.Genre;
import com.example.proiecttae.entities.Movie;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mihaela
 */

@Controller
public class GenreController {
    
    private GenreRepository genreRepository;
    private MovieRepository movieRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }
    
    @GetMapping("/genres")
    public String getGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "/genres";
    }
    
    @PostMapping("/genres")
    @Transactional
    public String addGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/addg";
    }
    
    @DeleteMapping("/genres/{id}")
    @Transactional
    public String deleteGenre(@PathVariable("id") Long id) {
        Genre g = genreRepository.findOne(id);
        List<Movie> movies = movieRepository.findAll();
        for(Movie m: movies) {
            if(m.getGenres().contains(g)) {
                m.getGenres().remove(g);
            }
        }
        genreRepository.delete(id);
        return "redirect:/genres";
    }
}
