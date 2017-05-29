/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proiecttae.dao;

import com.example.proiecttae.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mihaela
 */
public interface GenreRepository extends JpaRepository<Genre, Long>{
    
}
