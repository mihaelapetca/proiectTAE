/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proiecttae.dao;

import com.example.proiecttae.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mihaela
 */
public interface ActorRepository extends JpaRepository<Actor, Long>{
    
}
